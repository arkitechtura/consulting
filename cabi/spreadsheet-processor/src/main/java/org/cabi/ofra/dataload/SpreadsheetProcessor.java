package org.cabi.ofra.dataload;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cabi.ofra.dataload.configuration.ConfigurationHelper;
import org.cabi.ofra.dataload.configuration.ProcessorConfiguration;
import org.cabi.ofra.dataload.configuration.TemplateConfiguration;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.DefaultEventCollector;
import org.cabi.ofra.dataload.impl.DefaultTemplateProcessor;
import org.cabi.ofra.dataload.model.ITemplateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class SpreadsheetProcessor {
  private Reader configuration;
  private InputStream input;
  private String templateName;
  private String databasePropertiesFile;
  private static Logger logger = LoggerFactory.getLogger(SpreadsheetProcessor.class);
  private String user;

  public SpreadsheetProcessor(Reader configuration, InputStream input, String templateName, String databasePropertiesFile, String user) {
    this.configuration = configuration;
    this.input = input;
    this.templateName = templateName;
    this.databasePropertiesFile = databasePropertiesFile;
    this.user = user;
  }

  public void process() throws ProcessorException {
    try {
      Workbook workbook = WorkbookFactory.create(input);
      ProcessorConfiguration config = ConfigurationHelper.loadConfiguration(configuration);
      if (config != null) {
        TemplateConfiguration templateConfiguration = config.getTemplates().get(templateName);
        if (templateConfiguration != null) {
          ITemplateProcessor templateProcessor = createTemplateProcessor(templateName);
          IEventCollector eventCollector = new DefaultEventCollector();
          templateProcessor.processTemplate(workbook, templateConfiguration, eventCollector, databasePropertiesFile, user);
        }
        else {
          throw new ProcessorException(String.format("Error: template '%1$s' does not exist in configuration file. Unable to process spreadsheet", templateName));
        }
      }
      else {
        throw new ProcessorException("Error parsing configuration file");
      }
    }
    catch (IOException | InvalidFormatException | SAXException e) {
      throw new ProcessorException(e);
    }
  }

  private ITemplateProcessor createTemplateProcessor(String templateName) {
    return new DefaultTemplateProcessor();
  }
}
