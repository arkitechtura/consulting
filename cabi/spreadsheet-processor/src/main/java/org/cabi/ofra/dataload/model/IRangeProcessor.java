package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface IRangeProcessor extends IProcessor {
  public void processRow(IProcessingContext context, List<Cell> row);
}
