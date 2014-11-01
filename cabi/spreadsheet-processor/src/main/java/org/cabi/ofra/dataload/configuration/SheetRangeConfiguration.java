package org.cabi.ofra.dataload.configuration;

import java.util.*;

/**
* (c) 2014, Eduardo Quirós-Campos
*/
public class SheetRangeConfiguration {
  String processorReference;
  String start;
  int width;
  Map<String, String> arguments;
  List<SheetRangeColumnBindingConfiguration> columnBindings;

  public SheetRangeConfiguration() {
    columnBindings = new ArrayList<>();
  }

  public String getProcessorReference() {
    return processorReference;
  }

  public void setProcessorReference(String processorReference) {
    this.processorReference = processorReference;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void addArgument(String name, String value) {
    if (arguments == null) {
      arguments = new HashMap<String, String>();
    }
    arguments.put(name, value);
  }

  public void addColumnBindingConfiguration(SheetRangeColumnBindingConfiguration bindingConfiguration) {
    columnBindings.add(bindingConfiguration);
  }

  public Map<String, String> getArguments() {
    if (arguments == null) return Collections.emptyMap();
    return arguments;
  }

  public List<SheetRangeColumnBindingConfiguration> getColumnBindings() {
    return columnBindings;
  }
}
