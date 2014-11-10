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
    digester.addObjectCreate("processor-configuration", ProcessorConfiguration.class);
    // Adding Cell Processors
    digester.addFactoryCreate("processor-configuration/cell-processors/cell-processor", CellProcessorFactory.class);
    digester.addSetNext("processor-configuration/cell-processors/cell-processor", "addCellProcessor", ICellProcessor.class.getName());
    // Adding Range Processors
    digester.addFactoryCreate("processor-configuration/range-processors/range-processor", RangeProcessorFactory.class);
    digester.addSetNext("processor-configuration/range-processors/range-processor", "addRangeProcessor", IRangeProcessor.class.getName());
    // Adding templates
    digester.addObjectCreate("processor-configuration/templates/template", TemplateConfiguration.class);
    digester.addSetProperties("processor-configuration/templates/template");
    digester.addSetTop("processor-configuration/templates/template", "setProcessorConfiguration");
    digester.addSetNext("processor-configuration/templates/template", "addTemplateConfiguration");
    // Adding sheets
    digester.addObjectCreate("processor-configuration/templates/template/sheets/sheet", SheetConfiguration.class);
    digester.addSetProperties("processor-configuration/templates/template/sheets/sheet");
    digester.addSetTop("processor-configuration/templates/template/sheets/sheet", "setParentTemplate");
    digester.addSetNext("processor-configuration/templates/template/sheets/sheet", "addSheetConfiguration");
    // Adding cell processor configuration
    digester.addObjectCreate("processor-configuration/templates/template/sheets/sheet/cells/cell", SheetCellProcessorConfiguration.class);
    digester.addSetProperties("processor-configuration/templates/template/sheets/sheet/cells/cell");
    digester.addCallMethod("processor-configuration/templates/template/sheets/sheet/cells/cell/args/arg", "addArgument", 2);
    digester.addCallParam("processor-configuration/templates/template/sheets/sheet/cells/cell/args/arg", 0, "name");
    digester.addCallParam("processor-configuration/templates/template/sheets/sheet/cells/cell/args/arg", 1, "value");
    digester.addSetTop("processor-configuration/templates/template/sheets/sheet/cells/cell", "setParentSheet");
    digester.addSetNext("processor-configuration/templates/template/sheets/sheet/cells/cell", "addCellProcessorConfiguration");
    // Adding range processor configuration
    digester.addObjectCreate("processor-configuration/templates/template/sheets/sheet/ranges/range", SheetRangeConfiguration.class);
    digester.addSetProperties("processor-configuration/templates/template/sheets/sheet/ranges/range");
    digester.addCallMethod("processor-configuration/templates/template/sheets/sheet/ranges/range/args/arg", "addArgument", 2);
    digester.addCallParam("processor-configuration/templates/template/sheets/sheet/ranges/range/args/arg", 0, "name");
    digester.addCallParam("processor-configuration/templates/template/sheets/sheet/ranges/range/args/arg", 1, "value");
    digester.addObjectCreate("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding", SheetRangeColumnBindingConfiguration.class);
    digester.addSetProperties("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding");
    digester.addCallMethod("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding/args/arg", "addArgument", 2);
    digester.addCallParam("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding/args/arg", 0, "name");
    digester.addCallParam("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding/args/arg", 1, "value");
    digester.addSetTop("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding", "setParentRangeConfiguration");
    digester.addSetNext("processor-configuration/templates/template/sheets/sheet/ranges/range/column-bindings/column-binding", "addColumnBindingConfiguration");
    digester.addSetNext("processor-configuration/templates/template/sheets/sheet/ranges/range", "addRangeConfiguration");
    return digester;
  }
}
