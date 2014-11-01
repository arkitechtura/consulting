package org.cabi.ofra.dataload.model;

import java.io.Serializable;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface IProcessingContext {
  public void set(String name, Serializable value);
  public Serializable get(String name);
  public Map<String, ICellProcessor> getCellProcessors();
  public Map<String, IRangeProcessor> getRangeProcessors();
}
