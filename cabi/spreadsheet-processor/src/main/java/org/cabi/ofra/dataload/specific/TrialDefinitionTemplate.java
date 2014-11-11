package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.impl.BaseSheetProcessor;
import org.cabi.ofra.dataload.model.*;
import org.cabi.ofra.dataload.util.Utilities;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class TrialDefinitionTemplate {
  public static class TrialSheetProcessor extends BaseSheetProcessor {
    DatabaseService databaseService;
    @Override
    protected void process(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException{
      databaseService = context.getDatabaseService();
      Trial trial = extractTrial(context);
      databaseService.createOrUpdateTrial(trial);
    }

    private Trial extractTrial(IProcessingContext context) throws ProcessorException {
      Trial t = new Trial();
      t.setTrialUniqueId(context.getv("trialUniqueId"));
      t.setCountry(context.getv("countryCode"));
      validateCountry(t.getCountry());
      t.setRegionCode(context.getv("regionCode"));
      t.setVillageCode(context.getv("villageCode"));
      t.setDistrictCode(context.getv("districtCode"));
      t.setFarmerOrCentre(context.getv("farmerOrCentre"));
      t.setLeadResearcher(context.getv("leadResearcher"));
      t.setFieldAssistantName(context.getv("fieldAssistantName"));
      t.setFieldAssistantTelephone(context.getv("fieldAssistantTelephone"));
      t.setCropOne(context.getv("cropOne"));
      validateCrop(t.getCropOne());
      t.setCropTwo(context.getv("cropTwo"));
      validateCrop(t.getCropTwo());
      t.setLat(context.getv("latitude"));
      t.setLng(context.getv("longitude"));
      t.setUser(context.getUser());
      return t;
    }

    private void validateCrop(String cropCode) throws ProcessorException {
      if (cropCode != null) {
        if (!databaseService.existsCrop(cropCode)) {
          throw new ProcessorException(String.format("Crop code '%s' does not exist. Check the input template!", cropCode));
        }
      }
    }

    private void validateCountry(String country) throws ProcessorException {
      if (!databaseService.existsCountry(country)) {
        throw new ProcessorException(String.format("Country code '%s' does not exist. Check the input template!", country));
      }
    }
  }

  public static class BlocksSheetProcessor extends BaseSheetProcessor {
    @Override
    protected void beforeFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
      String mainTrialId = context.getv("trialUniqueId");
      String blockTrialUID = context.getv("blocksTrialUID");
      if (blockTrialUID == null) {
        throw new ProcessorException(String.format("Error: Trial UID is not present in %s sheet", sheet.getSheetName()));
      }
      if (mainTrialId == null) {
        String msg = "Warning: Trial Unique ID was not captured in the main 'Trial' sheet";
        logger.warn(msg);
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
      }
      else if (!mainTrialId.equals(blockTrialUID)) {
        String msg = String.format("Warning: UID captured in Trial sheet (%s) is different from the one present in the Blocks sheet (%s)", mainTrialId, blockTrialUID);
        logger.warn(msg);
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
      }
    }
  }

  public static class BlockRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      Block block = buildBlockFromRow(row, context);
      DatabaseService databaseService = context.getDatabaseService();
      databaseService.createOrUpdateBlock(block);
    }

    private Block buildBlockFromRow(List<Cell> row, IProcessingContext context) {
      Block block = new Block();
      block.setTrialUniqueId(context.getv("blocksTrialUID"));
      block.setBlockNumber(Utilities.getIntegerCellValue(row.get(0)));
      block.setLat(Utilities.getDoubleCellValue(row.get(1)));
      block.setLng(Utilities.getDoubleCellValue(row.get(2)));
      block.setLat2(Utilities.getDoubleCellValue(row.get(3)));
      block.setLng2(Utilities.getDoubleCellValue(row.get(4)));
      block.setLat3(Utilities.getDoubleCellValue(row.get(5)));
      block.setLng3(Utilities.getDoubleCellValue(row.get(6)));
      block.setLat4(Utilities.getDoubleCellValue(row.get(7)));
      block.setLng4(Utilities.getDoubleCellValue(row.get(8)));
      block.setElevation(Utilities.getDoubleCellValue(row.get(9)));
      return block;
    }
  }

  public static class PlotsSheetProcessor extends BaseSheetProcessor {
    @Override
    protected void beforeFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
      String mainTrialId = context.getv("trialUniqueId");
      String blockTrialUID = context.getv("plotsTrialUID");
      if (blockTrialUID == null) {
        throw new ProcessorException(String.format("Error: Trial UID is not present in %s sheet", sheet.getSheetName()));
      }
      if (mainTrialId == null) {
        String msg = "Warning: Trial Unique ID was not captured in the main 'Trial' sheet";
        logger.warn(msg);
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
      }
      else if (!mainTrialId.equals(blockTrialUID)) {
        String msg = String.format("Warning: UID captured in Trial sheet (%s) is different from the one present in the Plots sheet (%s)", mainTrialId, blockTrialUID);
        logger.warn(msg);
        eventCollector.addEvent(EventBuilder.createBuilder().withMessage(msg).withType(Event.EVENT_TYPE.WARNING).build());
      }
    }
  }
  public static class PlotRangeProcessor extends AbstractRangeProcessor {
    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      Plot plot = buildPlotFromRow(row, context);
      DatabaseService databaseService = context.getDatabaseService();
      databaseService.createOrUpdatePlot(plot);
    }

    private Plot buildPlotFromRow(List<Cell> row, IProcessingContext context) {
      Plot plot = new Plot();
      plot.setTrialUniqueId(context.getv("plotsTrialUID"));
      plot.setBlockNumber(Utilities.getIntegerCellValue(row.get(0)));
      plot.setPlotId(Utilities.getIntegerCellValue(row.get(1)));
      plot.setArea(Utilities.getDoubleCellValue(row.get(2)));
      plot.setNitrogen(Utilities.getDoubleCellValue(row.get(3)));
      plot.setPhosphorus(Utilities.getDoubleCellValue(row.get(4)));
      plot.setPotassium(Utilities.getDoubleCellValue(row.get(5)));
      plot.setSulphur(Utilities.getDoubleCellValue(row.get(6)));
      plot.setZinc(Utilities.getDoubleCellValue(row.get(7)));
      plot.setMagnesium(Utilities.getDoubleCellValue(row.get(8)));
      plot.setBoron(Utilities.getDoubleCellValue(row.get(9)));
      plot.setManure(Utilities.getDoubleCellValue(row.get(10)));
      plot.setCropOne(Utilities.getStringCellValue(row.get(11)));
      plot.setCropTwo(Utilities.getStringCellValue(row.get(12)));
      plot.setCropThree(Utilities.getStringCellValue(row.get(13)));
      plot.setObservations(Utilities.getStringCellValue(row.get(14)));
      return plot;
    }
  }
}
