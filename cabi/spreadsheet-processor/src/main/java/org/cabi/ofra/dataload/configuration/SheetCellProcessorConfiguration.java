package org.cabi.ofra.dataload.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
* (c) 2014, Eduardo Quirós-Campos
*/
public class SheetCellProcessorConfiguration {
  private String processorReference;
  private String location;
  private Map<String, String> arguments;
  private SheetConfiguration parentSheet;

  public SheetCellProcessorConfiguration() {
  }

  public String getProcessorReference() {
    return processorReference;
  }

  public void setProcessorReference(String processorReference) {
    this.processorReference = processorReference;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void addArgument(String name, String value) {
    if (arguments == null) {
      arguments = new HashMap<String, String>();
    }
    arguments.put(name, value);
  }

  public Map<String, String> getArguments() {
    if (arguments == null) return Collections.emptyMap();
    return arguments;
  }

  public SheetConfiguration getParentSheet() {
    return parentSheet;
  }

  public void setParentSheet(SheetConfiguration parentSheet) {
    this.parentSheet = parentSheet;
  }
}
