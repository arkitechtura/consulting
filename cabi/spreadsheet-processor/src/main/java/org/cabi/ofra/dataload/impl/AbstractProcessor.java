package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public abstract class AbstractProcessor implements IProcessor {
  protected String name;
  protected Logger logger = LoggerFactory.getLogger(this.getClass());
  protected Map<String, Object> arguments = new HashMap<>();

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setArgument(String name, Object value) {
    arguments.put(name, value);
  }

  @Override
  public void reset() {
    arguments.clear();
  }
}
