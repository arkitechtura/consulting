package org.cabi.ofra.dataload.configuration;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class DummyCellProcessor implements ICellProcessor {
  private String name;
  private Map<String, Object> args = new HashMap<>();
  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setArgument(String name, Object value) {
    args.put(name, value);
  }

  @Override
  public void reset() {

  }

  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {

  }
}
