package org.cabi.ofra.dataload.configuration;

import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IRangeProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class ProcessorConfiguration {
  private Map<String, ICellProcessor> cellProcessors;
  private Map<String, IRangeProcessor> rangeProcessors;
  private Map<String, TemplateConfiguration> templates;

  public ProcessorConfiguration() {
    cellProcessors = new HashMap<>();
    rangeProcessors = new HashMap<>();
    templates = new HashMap<>();
  }

  public void addCellProcessor(ICellProcessor cellProcessor) {
    cellProcessors.put(cellProcessor.getName(), cellProcessor);
  }

  public void addRangeProcessor(IRangeProcessor rangeProcessor) {
    rangeProcessors.put(rangeProcessor.getName(), rangeProcessor);
  }

  public void addTemplateConfiguration(TemplateConfiguration templateConfiguration) {
    templates.put(templateConfiguration.getName(), templateConfiguration);
  }

  public Map<String, ICellProcessor> getCellProcessors() {
    return cellProcessors;
  }

  public Map<String, IRangeProcessor> getRangeProcessors() {
    return rangeProcessors;
  }

  public Map<String, TemplateConfiguration> getTemplates() {
    return templates;
  }
}
