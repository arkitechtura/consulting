package org.cabi.ofra.dataload.model;

import org.cabi.ofra.dataload.db.DatabaseService;

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
  public DatabaseService getDatabaseService();
  public String getUser();
  public default <T extends Serializable> T getv(String name) {
    return (T) get(name);
  }
}
