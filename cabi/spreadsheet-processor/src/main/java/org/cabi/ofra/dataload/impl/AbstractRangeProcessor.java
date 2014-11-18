package org.cabi.ofra.dataload.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeColumnBindingConfiguration;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;
import org.cabi.ofra.dataload.util.Utilities;

import javax.annotation.processing.Processor;
import java.util.List;
import java.util.Map;

/**
 * Created by equiros on 07/11/14.
 */
public abstract class AbstractRangeProcessor extends AbstractProcessor implements IRangeProcessor {
  @Override
  public void processRow(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
    int i = 0;
    Map<Integer, SheetRangeColumnBindingConfiguration> bindings = rangeConfiguration.getColumnBindings();
    for (Cell cell : row) {
      if (Utilities.isBlank(cell) && rangeConfiguration.isRequireAll()) {
        throw new ProcessorException(String.format("Cell at position %d is null in range ", i));
      }
      SheetRangeColumnBindingConfiguration binding = bindings.get(i);
      if (binding != null) {
        processBinding(context, binding, cell, i, eventCollector);
      }
      i++;
    }
    process(context, row, eventCollector, rangeConfiguration);
  }

  protected abstract void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException;

  private void processBinding(IProcessingContext context, SheetRangeColumnBindingConfiguration binding, Cell cell, int columnIndex, IEventCollector eventCollector) throws ProcessorException {
    ICellProcessor processor = context.getCellProcessors().get(binding.getProcessorReference());
    if (processor != null) {
      processor.reset();
      for (Map.Entry<String, String> e : binding.getArguments().entrySet()) {
        processor.setArgument(e.getKey(), e.getValue());
      }
      processor.processCell(context, cell, eventCollector);
    }
    else {
      logger.warn(String.format("Processor reference %s not found for binding on column number %d", binding.getProcessorReference(), columnIndex));
    }
  }
}
