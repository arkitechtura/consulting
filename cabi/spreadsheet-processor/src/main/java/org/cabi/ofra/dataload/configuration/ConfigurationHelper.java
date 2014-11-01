package org.cabi.ofra.dataload.configuration;

import org.apache.commons.digester.Digester;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.cabi.ofra.dataload.model.IRangeProcessor;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Reader;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class ConfigurationHelper {
  public static ProcessorConfiguration loadConfiguration(Reader reader) throws IOException, SAXException {
    Digester digester = buildDigester();
    return (ProcessorConfiguration) digester.parse(reader);
  }

  private static Digester buildDigester() {
    Digester digester = new Digester();
    digester.setValidating(false);
    // Processor Configuration object
    digester.addObjectCreate("processorReference-configuration", ProcessorConfiguration.class);
    // Adding Cell Processors
    digester.addFactoryCreate("processorReference-configuration/cell-processors/cell-processorReference", CellProcessorFactory.class);
    digester.addSetNext("processorReference-configuration/cell-processors/cell-processorReference", "addCellProcessor", ICellProcessor.class.getName());
    // Adding Range Processors
    digester.addFactoryCreate("processorReference-configuration/range-processors/range-processorReference", RangeProcessorFactory.class);
    digester.addSetNext("processorReference-configuration/range-processors/range-processorReference", "addRangeProcessor", IRangeProcessor.class.getName());
    // Adding templates
    digester.addObjectCreate("processorReference-configuration/templates/template", TemplateConfiguration.class);
    digester.addSetProperties("processorReference-configuration/templates/template");
    digester.addSetTop("processorReference-configuration/templates/template", "setProcessorConfiguration");
    digester.addSetNext("processorReference-configuration/templates/template", "addTemplateConfiguration");
    // Adding sheets
    digester.addObjectCreate("processorReference-configuration/templates/template/sheets/sheet", SheetConfiguration.class);
    digester.addSetProperties("processorReference-configuration/templates/template/sheets/sheet");
    digester.addSetTop("processorReference-configuration/templates/template/sheets/sheet", "setParentTemplate");
    digester.addSetNext("processorReference-configuration/templates/template/sheets/sheet", "addSheetConfiguration");
    // Adding cell processorReference configuration
    digester.addObjectCreate("processorReference-configuration/templates/template/sheets/sheet/cells/cell", SheetCellProcessorConfiguration.class);
    digester.addSetProperties("processorReference-configuration/templates/template/sheets/sheet/cells/cell");
    digester.addCallMethod("processorReference-configuration/templates/template/sheets/sheet/cells/cell/args", "addArgument", 2);
    digester.addCallParam("processorReference-configuration/templates/template/sheets/sheet/cells/cell/args/arg", 0, "name");
    digester.addCallParam("processorReference-configuration/templates/template/sheets/sheet/cells/cell/args/arg", 1, "value");
    digester.addSetTop("processorReference-configuration/templates/template/sheets/sheet/cells/cell", "setParentSheet");
    digester.addSetNext("processorReference-configuration/templates/template/sheets/sheet/cells/cell", "addCellProcessorConfiguration");
    // Adding range processorReference configuration
    digester.addObjectCreate("processorReference-configuration/templates/template/sheets/sheet/ranges/range", SheetRangeConfiguration.class);
    digester.addSetProperties("processorReference-configuration/templates/template/sheets/sheet/ranges/range");
    digester.addCallMethod("processorReference-configuration/templates/template/sheets/sheet/ranges/range/args", "addArgument", 2);
    digester.addCallParam("processorReference-configuration/templates/template/sheets/sheet/ranges/range/args/arg", 0, "name");
    digester.addCallParam("processorReference-configuration/templates/template/sheets/sheet/ranges/range/args/arg", 1, "value");
    digester.addObjectCreate("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding", SheetRangeColumnBindingConfiguration.class);
    digester.addSetProperties("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding");
    digester.addCallMethod("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding/args", "addArgument", 2);
    digester.addCallParam("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding/args/arg", 0, "name");
    digester.addCallParam("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding/args/arg", 1, "value");
    digester.addSetTop("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding", "setParentRangeConfiguration");
    digester.addSetNext("processorReference-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding", "addColumnBindingConfiguration");
    digester.addSetNext("processorReference-configuration/templates/template/sheets/sheet/ranges/range", "addRangeConfiguration");
    return digester;
  }
}
