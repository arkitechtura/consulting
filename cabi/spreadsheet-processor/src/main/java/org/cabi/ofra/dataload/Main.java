package org.cabi.ofra.dataload;

import org.apache.commons.cli.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class Main {
  private static Logger logger = LoggerFactory.getLogger(Main.class);

  private static Options createOptions() {
    Options options = new Options();
    options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use the given file as input for processing").isRequired().create("input"));
    options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use the given file as configuration").create("config"));
    options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use the given file as database configuration properties").create("database"));
    options.addOption(OptionBuilder.withArgName("template").hasArg().withDescription("process input file using the given template").isRequired().create("template"));
    options.addOption(OptionBuilder.withArgName("username").hasArg().withDescription("user the given user in all database operations").isRequired().create("user"));
    return options;
  }

  private static Properties loadProgramProperties() throws IOException {
    Properties properties = new Properties();
    properties.load(Main.class.getClassLoader().getResourceAsStream("program.properties"));
    return properties;
  }

  private static CommandLineParser parser = new GnuParser();
  private static HelpFormatter helpFormatter = new HelpFormatter();

  public static void main(String[] args) {
    Options options = createOptions();
    try {
      Properties programProperties = loadProgramProperties();
      logger.info(String.format("%1$s - Version %2$s", programProperties.getProperty("program.name"), programProperties.getProperty("program.version")));
      CommandLine commandLine = parser.parse(options, args);
      start(commandLine);
    }
    catch (ParseException e) {
      helpFormatter.printHelp("ssprocessor", options);
    }
    catch (Exception e) {
      logger.error("Error processing spreadsheet", e);
    }
  }

  private static void start(CommandLine commandLine) throws IOException, ProcessorException {
    Reader configReader;
    if (commandLine.hasOption("config")) {
      configReader = new BufferedReader(new FileReader(commandLine.getOptionValue("config")));
    }
    else {
      configReader = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("default-configuration.xml")));
    }
    SpreadsheetProcessor processor = new SpreadsheetProcessor(configReader, new BufferedInputStream(new FileInputStream(commandLine.getOptionValue("input"))),
            commandLine.getOptionValue("template"), commandLine.getOptionValue("database"), commandLine.getOptionValue("user"));
    processor.process();
  }
}
