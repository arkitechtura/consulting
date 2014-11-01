package org.cabi.ofra.dataload.impl;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetCellProcessorConfiguration;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.ISheetProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    // next, fire the range processors

  }

  protected abstract void afterFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context);

  protected abstract void beforeFireCellProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context);

  private void fireCellProcessors(Sheet sheet, SheetConfiguration configuration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
    for (SheetCellProcessorConfiguration cellProcessorConfiguration : configuration.getCellProcessorConfigurations()) {
      ICellProcessor cellProcessor = getCellProcessor(cellProcessorConfiguration.getProcessorReference(), context);
      if (cellProcessor != null) {
        CellReference ref = new CellReference(cellProcessorConfiguration.getLocation());
        if (ref.getRow() < sheet.getLastRowNum()) {
          Row row = sheet.getRow(ref.getRow());
          if (ref.getCol() < row.getLastCellNum()) {
            cellProcessor.reset();
            for (Map.Entry<String, String> e : cellProcessorConfiguration.getArguments().entrySet()) {
              cellProcessor.setArgument(e.getKey(), e.getValue());
            }
            cellProcessor.processCell(context, row.getCell(ref.getCol()));
          }
          else {
            String msg = String.format("Warning: Cell processor '%s' references column number %d, which does not exist", cellProcessor.getName(), ref.getCol());
            eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
            logger.warn(msg);
          }
        }
        else {
          String msg = String.format("Warning: Cell processor '%s' references row number %d, which does not exist", cellProcessor.getName(), ref.getRow());
          eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
          logger.warn(msg);
        }
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

  protected abstract void process(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context);
}
