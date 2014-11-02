package org.cabi.ofra.dataload.util;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class Utilities {
  public static boolean validateCellReference(CellReference ref, Sheet sheet) {
    if (ref.getRow() >= sheet.getLastRowNum()) return false;
    Row row = sheet.getRow(ref.getRow());
    if (ref.getCol() >= row.getLastCellNum()) return false;
    return true;
  }
}
