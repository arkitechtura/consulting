package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class DefaultProcessingContext implements IProcessingContext {
  private Map<String, Serializable> context;
  private Map<String, ICellProcessor> cellProcessors = Collections.EMPTY_MAP;
  private Map<String, IRangeProcessor> rangeProcessors = Collections.EMPTY_MAP;
  private DatabaseService databaseService;
  private String user;

  public DefaultProcessingContext(Map<String, ICellProcessor> cellProcessors, Map<String, IRangeProcessor> rangeProcessors, DatabaseService databaseService, String user) {
    this.cellProcessors = cellProcessors;
    this.rangeProcessors = rangeProcessors;
    this.databaseService = databaseService;
    context = new HashMap<>();
    this.user = user;
  }

  @Override
  public void set(String name, Serializable value) {
    context.put(name, value);
  }

  @Override
  public Serializable get(String name) {
    return context.get(name);
  }

  @Override
  public Map<String, ICellProcessor> getCellProcessors() {
    return cellProcessors;
  }

  @Override
  public Map<String, IRangeProcessor> getRangeProcessors() {
    return rangeProcessors;
  }

  @Override
  public DatabaseService getDatabaseService() {
    return databaseService;
  }

  @Override
  public String getUser() {
    return null;
  }
}
