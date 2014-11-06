package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.EventBuilder;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractProcessor;
import org.cabi.ofra.dataload.impl.BaseSheetProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.IRangeProcessor;
import org.cabi.ofra.dataload.model.Trial;

import java.util.List;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class TrialDefinitionTemplate {
  public static class TrialSheetProcessor extends BaseSheetProcessor {
    @Override
    protected void process(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException{
      Trial trial = extractTrial(context);
      DatabaseService databaseService = context.getDatabaseService();
      databaseService.createOrUpdateTrial(trial);
    }

    private Trial extractTrial(IProcessingContext context) {
      Trial t = new Trial();
      t.setTrialUniqueId(context.getv("trialUniqueId"));
      t.setCountry(context.getv("countryCode"));
      t.setRegionCode(context.getv("regionCode"));
      t.setVillageCode(context.getv("villageCode"));
      t.setDistrictCode(context.getv("districtCode"));
      t.setFarmerOrCentre(context.getv("farmerOrCentre"));
      t.setLeadResearcher(context.getv("leadResearcher"));
      t.setFieldAssistantName(context.getv("fieldAssistantName"));
      t.setFieldAssistantTelephone(context.getv("fieldAssistantTelephone"));
      t.setCropOne(context.getv("cropOne"));
      t.setCropTwo(context.getv("cropTwo"));
      t.setLat(context.getv("latitude"));
      t.setLng(context.getv("longitude"));
      t.setUser(context.getUser());
      t.setValid(true);
      return t;
    }
  }

  public static class BlocksSheetProcessor extends BaseSheetProcessor {
    @Override
    protected void beforeFireRangeProcessors(Sheet sheet, SheetConfiguration sheetConfiguration, IEventCollector eventCollector, IProcessingContext context) throws ProcessorException {
      String mainTrialId = context.getv("trialUniqueId");
      String blockTrialUID = context.getv("blockTrialUID");
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

  public static class BlockRangeProcessor extends AbstractProcessor implements IRangeProcessor {
    @Override
    public void processRow(IProcessingContext context, List<Cell> row) {

    }
  }
}
