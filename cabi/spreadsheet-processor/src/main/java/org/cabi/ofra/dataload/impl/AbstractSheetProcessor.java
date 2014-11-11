package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetCellProcessorConfiguration;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;
import org.cabi.ofra.dataload.model.ISheetProcessor;
import org.cabi.ofra.dataload.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public abstract class AbstractSheetProcessor implements ISheetProcessor {
  protected static Logger logger = LoggerFactory.getLogger(AbstractSheetProcessor.class);

  public void processSheet(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    // first, let's fire the cell processors
    beforeFireCellProcessors(sheet, sheetConfiguration, eventCollector, context);
    fireCellProcessors(sheet, sheetConfiguration, eventCollector, context);
    afterFireCellProcessors(sheet, sheetConfiguration, eventCollector, context);
    // then, fire range processors
    beforeFireRangeProcessors(sheet, sheetConfiguration, eventCollector, context);
    fireRangeProcessors(sheet, sheetConfiguration, eventCollector, context);
    afterFireRangeProcessors(sheet, sheetConfiguration, eventCollector, context);
    // fire the main process on the sheet
    process(sheet, sheetConfiguration, eventCollector, context);
  }

  protected abstract void afterFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  protected abstract void beforeFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  private void fireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    for (SheetRangeConfiguration rangeConfiguration : sheetConfiguration.getRangeConfigurations()) {
      IRangeProcessor rangeProcessor = getRangeProcessor(rangeConfiguration.getProcessorReference(), context);
      if (rangeProcessor != null) {
        CellReference ref = new CellReference(rangeConfiguration.getStart());
        if (!Utilities.validateCellReference(ref, sheet)) {
          String msg = String.format("Warning: Range processor '%s' references cell %s as range start, which is invalid in sheet '%s'", rangeProcessor.getName(), ref.toString(), sheet.getSheetName());
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
          logger.warn(msg);
          continue;
        }
        for (int i = ref.getRow(); i <= sheet.getLastRowNum(); i++) {
          Row row = sheet.getRow(i);
          Cell left = row.getCell(ref.getCol(), Row.RETURN_BLANK_AS_NULL);
          if (left != null) {
            // this means there is a valid row
            List<Cell> r = new ArrayList<>(rangeConfiguration.getWidth());
            int lastColumn = Math.max(row.getLastCellNum(), ref.getCol() + rangeConfiguration.getWidth() - 1);
            for (int j = ref.getCol(); j < lastColumn; j++) {
              r.add(row.getCell(j, Row.CREATE_NULL_AS_BLANK));
            }
            try {
              rangeProcessor.processRow(context, r, eventCollector, rangeConfiguration);
            }
            catch (ProcessorException e) {
              String msg = String.format("Error processing row #%d on RangeProcessor '%s'", i, rangeProcessor.getName());
              logger.warn(msg, e);
              eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withException(e).withType(Event.EVENT_TYPE.WARNING).build());
            }
          }
        }
      }
      else {
        String msg = String.format("Warning: range processor reference %s not found while processing sheet '%s'", rangeConfiguration.getProcessorReference(), sheet.getSheetName());
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        logger.warn(msg);
      }
    }
  }

  protected abstract void afterFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  protected abstract void beforeFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;

  private void fireCellProcessors(Sheet sheet, SheetConfiguration configuration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    for (SheetCellProcessorConfiguration cellProcessorConfiguration : configuration.getCellProcessorConfigurations()) {
      ICellProcessor cellProcessor = getCellProcessor(cellProcessorConfiguration.getProcessorReference(), context);
      if (cellProcessor != null) {
        CellReference ref = new CellReference(cellProcessorConfiguration.getLocation());
        if (!Utilities.validateCellReference(ref, sheet)) {
          String msg = String.format("Warning: Cell processor '%s' references cell %s, which is invalid in sheet '%s'", cellProcessor.getName(), ref.toString(), sheet.getSheetName());
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
          logger.warn(msg);
          continue;
        }
        Row row = sheet.getRow(ref.getRow());
        cellProcessor.reset();
        for (Map.Entry<String, String> e : cellProcessorConfiguration.getArguments().entrySet()) {
          cellProcessor.setArgument(e.getKey(), e.getValue());
        }
        cellProcessor.processCell(context, row.getCell(ref.getCol(), Row.RETURN_BLANK_AS_NULL), eventCollector);
      }
      else {
        String msg = String.format("Warning: processor reference %s not found while processing sheet '%s'", cellProcessorConfiguration.getProcessorReference(), sheet.getSheetName());
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        logger.warn(msg);
      }
    }
  }

  private ICellProcessor getCellProcessor(String processorReference, IProcessingContext context) {
    return context.getCellProcessors().get(processorReference);
  }

  private IRangeProcessor getRangeProcessor(String processorReference, IProcessingContext context) {
    return context.getRangeProcessors().get(processorReference);
  }

  protected abstract void process(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;
}
