package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.configuration.TemplateConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.ISheetProcessor;
import org.cabi.ofra.dataload.model.ITemplateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class DefaultTemplateProcessor implements ITemplateProcessor {
  private static Logger logger = LoggerFactory.getLogger(DefaultTemplateProcessor.class);

  public IProcessingContext processTemplate(Workbook workbook, TemplateConfiguration configuration, IEventCollector eventCollector) throws ProcessorException {
    IProcessingContext context = new DefaultProcessingContext(configuration.getProcessorConfiguration().getCellProcessors(), configuration.getProcessorConfiguration().getRangeProcessors());
    for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
      Sheet sheet = workbook.getSheetAt(s);
      SheetConfiguration sheetConfiguration = configuration.getSheet(sheet.getSheetName());
      if (sheetConfiguration != null) {
        try {
          ISheetProcessor sheetProcessor = createSheetProcessor(sheetConfiguration.getImplementationClass());
          sheetProcessor.processSheet(sheet, sheetConfiguration, eventCollector, context);
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
          throw new ProcessorException(String.format("Error while processing sheet '%1$s'", sheet.getSheetName()), e);
        }
      }
      else {
        String msg = String.format("Warning: sheet configuration not found for sheet '%1$s' in template '%2$s'", sheet.getSheetName(), configuration.getName());
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        logger.warn(msg);
      }
    }
    return context;
  }

  private ISheetProcessor createSheetProcessor(String clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class cls = DefaultTemplateProcessor.class.getClassLoader().loadClass(clazz);
    return (ISheetProcessor) cls.newInstance();
  }
}
