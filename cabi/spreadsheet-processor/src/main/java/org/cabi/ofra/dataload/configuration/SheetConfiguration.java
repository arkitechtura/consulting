package org.cabi.ofra.dataload.configuration;

import java.util.*;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class SheetConfiguration {
  private String name;
  private List<SheetCellProcessorConfiguration> cellProcessorConfigurations;
  private List<SheetRangeConfiguration> rangeConfigurations;
  private boolean required;
  private String implementationClass;
  private TemplateConfiguration parentTemplate;

  public SheetConfiguration() {
    cellProcessorConfigurations = new ArrayList<>();
    rangeConfigurations = new ArrayList<>();
  }

  public void addCellProcessorConfiguration(SheetCellProcessorConfiguration config) {
    cellProcessorConfigurations.add(config);
  }

  public void addRangeConfiguration(SheetRangeConfiguration config) {
    rangeConfigurations.add(config);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public boolean isRequired() {
    return required;
  }

  public String getImplementationClass() {
    return implementationClass;
  }

  public void setImplementationClass(String implementationClass) {
    this.implementationClass = implementationClass;
  }

  public List<SheetCellProcessorConfiguration> getCellProcessorConfigurations() {
    return cellProcessorConfigurations;
  }

  public List<SheetRangeConfiguration> getRangeConfigurations() {
    return rangeConfigurations;
  }

  public TemplateConfiguration getParentTemplate() {
    return parentTemplate;
  }

  public void setParentTemplate(TemplateConfiguration parentTemplate) {
    this.parentTemplate = parentTemplate;
  }
}
