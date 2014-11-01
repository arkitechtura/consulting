package org.cabi.ofra.dataload.configuration;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.cabi.ofra.dataload.model.ICellProcessor;
import org.xml.sax.Attributes;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class CellProcessorFactory implements ObjectCreationFactory {
  private Digester digester;

  @Override
  public Object createObject(Attributes attributes) throws Exception {
    String clazz = attributes.getValue("class");
    String name = attributes.getValue("name");
    ICellProcessor processor = createProcessor(clazz);
    processor.setName(name);
    return processor;
  }

  private ICellProcessor createProcessor(String clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    digester.getLogger().debug("Attempting to create CellProcessor from class: '" + clazz + "'");
    Class c = digester.getClassLoader().loadClass(clazz);
    return (ICellProcessor) c.newInstance();
  }

  @Override
  public Digester getDigester() {
    return digester;
  }

  @Override
  public void setDigester(Digester digester) {
    this.digester = digester;
  }
}
