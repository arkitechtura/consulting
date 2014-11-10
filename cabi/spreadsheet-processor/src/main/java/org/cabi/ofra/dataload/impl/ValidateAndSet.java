package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.util.Utilities;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class ValidateAndSet extends AbstractProcessor implements ICellProcessor {
  private static final String KEY_REGEX = "regex";
  private static final String KEY_VARIABLENAME = "variableName";
  private static final String KEY_TOSTRING = "toString";

  @Override
  public void processCell(IProcessingContext context, Cell cell, IEventCollector eventCollector) throws ProcessorException {
    if (cell == null) {
      String msg = "Cell is null. Processing aborted on ValidateAndSet processor";
      eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
      logger.warn(msg);
      return;
    }
    if (!arguments.containsKey(KEY_VARIABLENAME)) {
      throw new ProcessorException("ValidateAndSet processor requires 'variableName' argument");
    }
    String variableName = arguments.get(KEY_VARIABLENAME).toString();
    if (arguments.containsKey(KEY_REGEX)) {
      if (!Pattern.matches(arguments.get(KEY_REGEX).toString(), cell.getStringCellValue())) {
        String msg = String.format("Value '%s' does not match pattern '%s'. Variable '%s' will not be set", cell.getStringCellValue(), arguments.get(KEY_REGEX).toString(),
                variableName);
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
        logger.warn(msg);
      }
    }
    Serializable val = Utilities.getCellValue(cell);
    if (arguments.containsKey(KEY_TOSTRING)) {
      val = String.valueOf(val);
    }
    context.set(variableName, val);
  }
}
