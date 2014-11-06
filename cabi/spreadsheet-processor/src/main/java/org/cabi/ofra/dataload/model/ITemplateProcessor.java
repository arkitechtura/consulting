package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Workbook;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.TemplateConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.IEventCollector;

import java.util.List;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface ITemplateProcessor {
  public IProcessingContext processTemplate(Workbook workbook, TemplateConfiguration configuration, IEventCollector eventCollector, String databasePropertiesFile, String user) throws ProcessorException;
}
