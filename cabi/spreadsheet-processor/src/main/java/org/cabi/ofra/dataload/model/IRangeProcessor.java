package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface IRangeProcessor {
  public void setName(String name);
  public String getName();
  public void setArgument(String name, Object value);
  public void reset();
  public void processRow(IProcessingContext context, List<Cell> row);
}
