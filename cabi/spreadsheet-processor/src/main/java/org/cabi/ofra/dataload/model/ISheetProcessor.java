package org.cabi.ofra.dataload.model;

import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.IEventCollector;

import java.util.List;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface ISheetProcessor {
  public void processSheet(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException;
}
