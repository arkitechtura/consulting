package org.cabi.ofra.dataload.model;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface IProcessor {
  public void setName(String name);
  public String getName();
  public void setArgument(String name, Object value);
  public void reset();
}
