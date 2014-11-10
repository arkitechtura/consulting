package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Utilities;

import java.io.Serializable;

/**
 * Created by equiros on 07/11/14.
 */
public class CheckPresent extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_MESSAGE = "message";
  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    if (cell == null || Utilities.getStringCellValue(cell).isEmpty()) {
      String msg;
      if (arguments.containsKey(KEY_MESSAGE)) {
        msg = arguments.get(KEY_MESSAGE).toString();
      }
      else {
        msg = String.format("Cell failed CheckPresent validation!");
      }
      throw new ProcessorException(msg);
    }
  }
}
