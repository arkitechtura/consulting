package org.cabi.ofra.dataload.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class ConfigurationHelperTest {
  String testConfigXML =
          "<processor-configuration>\n" +
          "    <cell-processors>\n" +
          "        <cell-processor name=\"dummy-cell-processor\" class=\"org.cabi.ofra.dataload.configuration.DummyCellProcessor\"/>\n" +
          "    </cell-processors>\n" +
          "    <range-processors>\n" +
          "        <range-processor name=\"dummy-range-processor\" class=\"org.cabi.ofra.dataload.configuration.DummyRangeProcessor\"/>\n" +
          "    </range-processors>\n" +
          "    <templates>\n" +
          "        <template name=\"test-template\">\n" +
          "            <sheets>\n" +
          "                <sheet name=\"test-sheet\" required=\"true\">\n" +
          "                    <cells>\n" +
          "                        <cell processorReference=\"dummy-cell-processor\" location=\"B2\">\n" +
          "                            <args>\n" +
          "                                <arg name=\"arg\" value=\"value\"/>\n" +
          "                            </args>\n" +
          "                        </cell>\n" +
          "                    </cells>\n" +
          "                    <ranges>\n" +
          "                        <range start=\"I2\" width=\"4\" processorReference=\"dummy-range-processor\">\n" +
          "                            <args>\n" +
          "                                <arg name=\"arg\" value=\"value\"/>\n" +
          "                            </args>\n" +
          "                            <column-bindings>\n" +
          "                                <column-binding column=\"0\" processorReference=\"dummy-cell-processor\">\n" +
          "                                    <args>\n" +
          "                                        <arg name=\"arg\" value=\"value\"/>\n" +
          "                                    </args>\n" +
          "                                </column-binding>\n" +
          "                            </column-bindings>\n" +
          "                        </range>\n" +
          "                    </ranges>\n" +
          "                </sheet>\n" +
          "            </sheets>\n" +
          "        </template>\n" +
          "    </templates>\n" +
          "</processor-configuration>";
  @Test
  public void testBasicConfiguration() throws IOException, SAXException {
    StringReader reader = new StringReader(testConfigXML);
    ProcessorConfiguration config = ConfigurationHelper.loadConfiguration(reader);
    reader.close();
    Assert.assertNotNull(config);
    // assert processorReference creation
    Assert.assertFalse(config.getCellProcessors().isEmpty());
    Assert.assertFalse(config.getRangeProcessors().isEmpty());
    Assert.assertTrue(config.getCellProcessors().containsKey("dummy-cell-processor"));
    Assert.assertTrue(config.getRangeProcessors().containsKey("dummy-range-processor"));
    // assert template creation
    Assert.assertFalse(config.getTemplates().isEmpty());
    Assert.assertTrue(config.getTemplates().containsKey("test-template"));
    TemplateConfiguration template = config.getTemplates().get("test-template");
    // assert sheet creation
    Assert.assertFalse(template.getSheets().isEmpty());
    Assert.assertTrue(template.getSheets().containsKey("test-sheet"));
    SheetConfiguration sheet = template.getSheet("test-sheet");
    // assert cell/range configurations on sheet
    Assert.assertFalse(sheet.getCellProcessorConfigurations().isEmpty());
    Assert.assertFalse(sheet.getRangeConfigurations().isEmpty());
    Assert.assertTrue(sheet.getCellProcessorConfigurations().get(0).getProcessorReference().equals("dummy-cell-processor"));
    Assert.assertTrue(sheet.getCellProcessorConfigurations().get(0).getArguments().containsKey("arg"));
    SheetRangeConfiguration rangeConfiguration = sheet.getRangeConfigurations().get(0);
    Assert.assertTrue(rangeConfiguration.getProcessorReference().equals("dummy-range-processor"));
    Assert.assertTrue(rangeConfiguration.getArguments().containsKey("arg"));
    // assert column bindings on range configuration
    Assert.assertFalse(rangeConfiguration.getColumnBindings().isEmpty());
    Assert.assertFalse(rangeConfiguration.getColumnBindings().get(0).getArguments().isEmpty());
    Assert.assertTrue(rangeConfiguration.getColumnBindings().get(0).getArguments().containsKey("arg"));
    Assert.assertTrue(rangeConfiguration.getColumnBindings().get(0).getProcessorReference().equals("dummy-cell-processor"));
  }
}
