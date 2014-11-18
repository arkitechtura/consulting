package org.cabi.ofra.dataload.util;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class Utilities {
  private static Logger logger = LoggerFactory.getLogger(Utilities.class);

  public static boolean validateCellReference(CellReference ref, Sheet sheet) {
/*
    if (ref.getRow() >= sheet.getLastRowNum()) return false;
    Row row = sheet.getRow(ref.getRow());
    if (ref.getCol() >= row.getLastCellNum()) return false;
*/
    return true;
  }

  private static InputStream getPropertiesStream(String propertiesFile) throws FileNotFoundException {
    if (propertiesFile != null && new File(propertiesFile).exists()) {
      logger.info("Attempting to load database properties from file '%s'");
      return new FileInputStream(propertiesFile);
    }
    if (new File("database.properties").exists()) {
      logger.info("Attempting to load database properties from 'database.properties' in current directory");
      return new FileInputStream("database.properties");
    }
    logger.info("Attempting to load database properties from JAR resource 'database.properties'");
    return Utilities.class.getClassLoader().getResourceAsStream("database.properties");
  }

  public static Properties loadDatabaseProperties(String propertiesFile) throws IOException {
    InputStream is = getPropertiesStream(propertiesFile);
    if (is != null) {
      logger.info("Database properties successfully loaded!");
      Properties properties = new Properties();
      properties.load(is);
      return properties;
    }
    else {
      logger.error("Unable to load database properties");
      return null;
    }
  }

  private static Serializable evaluateCell(Cell cell, int type) {
    switch (type) {
      case Cell.CELL_TYPE_STRING:
        return cell.getStringCellValue();
      case Cell.CELL_TYPE_NUMERIC:
        return cell.getNumericCellValue();
      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();
      case Cell.CELL_TYPE_BLANK:
        return "";
      case Cell.CELL_TYPE_FORMULA:
        return evaluateCell(cell, cell.getCachedFormulaResultType());
      default:
        // case error
        return cell.getErrorCellValue();
    }
  }

  private static int getCellType(Cell cell) {
    return cell.getCellType() == Cell.CELL_TYPE_FORMULA ? cell.getCachedFormulaResultType() : cell.getCellType();
  }

  public static boolean isBlank(Cell cell) {
    return (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK);
  }

  public static Serializable getCellValue(Cell cell) {
    return evaluateCell(cell, cell.getCellType());
  }

  public static String getStringCellValue(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return "";
    }
    Serializable v = getCellValue(cell);
    return v.getClass().equals(String.class) ? (String) v : String.valueOf(v);
  }

  public static Date getDateCellValue(Cell cell) throws ParseException {
    switch (getCellType(cell)) {
      case Cell.CELL_TYPE_BLANK:
        return new Date(0);
      case Cell.CELL_TYPE_NUMERIC:
        return cell.getDateCellValue();
      case Cell.CELL_TYPE_STRING:
        return parseDate(cell.getStringCellValue());
      default:
        return null;
    }
  }

  private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  private static Date parseDate(String dateString) throws ParseException {
    return formatter.parse(dateString);
  }

  public static double getDoubleCellValue(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return Double.MIN_VALUE;
    }
    Serializable v = getCellValue(cell);
    if (v.getClass().equals(Double.class) || v.getClass().equals(Float.class)) {
      return (Double) v;
    }
    else if (v.getClass().equals(Boolean.class)) {
      Boolean b = (Boolean) v;
      return b ? 1.0 : 0.0;
    }
    else {
      return Double.valueOf(v.toString());
    }
  }

  public static int getIntegerCellValue(Cell cell) {
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return Integer.MIN_VALUE;
    }
    Serializable v = getCellValue(cell);
    if (v.getClass().equals(Double.class) || v.getClass().equals(Float.class)) {
      return ((Double) v).intValue();
    }
    else if (v.getClass().equals(Boolean.class)) {
      Boolean b = (Boolean) v;
      return b ? 1 : 0;
    }
    else {
      return Integer.valueOf(v.toString());
    }
  }
  private static Pattern basePattern = Pattern.compile("([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([a-zA-Z0-9]+)_([\\d]{4})([a-zA-Z0-9]{2})[_]?([a-zA-Z0-9]*)");

  public static Matcher matchUid(String uid) {
    Matcher m = basePattern.matcher(uid);
    if (m.matches()) return m;
    return null;
  }

  public static Pair<String, String> matchTrialBase(String uid) {
    Matcher m = matchUid(uid);
    if (m != null) {
      return new Pair<>(String.format("%s_%s_%s_%s_%s_%s%s", m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7)), m.group(8));
    }
    return null;
  }

  public static String extractTrialUniqueId(String uid) {
    Pair<String, String> p = matchTrialBase(uid);
    if (p == null) return null;
    return p.car();
  }

  public static void validateTrial(DatabaseService databaseService,  String trialUid) throws ProcessorException {
    if (!databaseService.existsTrialByUniqueId(trialUid)) {
      throw new ProcessorException(String.format("Referenced trial '%s' does not exist in database", trialUid));
    }
  }

}
