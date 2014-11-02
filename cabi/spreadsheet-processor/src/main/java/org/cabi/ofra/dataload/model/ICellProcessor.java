package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.IEventCollector;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface ICellProcessor {
  public void setName(String name);
  public String getName();
  public void setArgument(String name, Object value);
  public void reset();
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException;
}
