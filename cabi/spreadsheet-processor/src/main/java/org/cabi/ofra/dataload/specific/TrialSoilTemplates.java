package org.cabi.ofra.dataload.specific;

import org.apache.poi.ss.usermodel.Cell;
import org.cabi.ofra.dataload.ProcessorException;
import org.cabi.ofra.dataload.configuration.SheetRangeConfiguration;
import org.cabi.ofra.dataload.db.DatabaseService;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.impl.AbstractRangeProcessor;
import org.cabi.ofra.dataload.model.IProcessingContext;
import org.cabi.ofra.dataload.model.SoilSample;
import org.cabi.ofra.dataload.util.Pair;
import org.cabi.ofra.dataload.util.Utilities;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by equiros on 11/12/2014.
 */
public class TrialSoilTemplates {
  private static final String KEY_PATTERN = "regex";
  private static final String KEY_GROUP = "group";
  private static Pattern defaultSampleCodePattern = Pattern.compile("([\\D]+)([\\d]+)");
  private static int defaultSampleCodeGroup = 2;

  private static int getSampleId(Map<String, Object> args, String sampleIdStr) {
    Pattern pattern = defaultSampleCodePattern;
    int group = defaultSampleCodeGroup;
    if (args.containsKey(KEY_PATTERN)) {
      pattern = Pattern.compile(args.get(KEY_PATTERN).toString());
      if (args.containsKey(KEY_GROUP)) {
        defaultSampleCodeGroup = Integer.valueOf(args.get(KEY_GROUP).toString());
      }
    }
    Matcher matcher = pattern.matcher(sampleIdStr);
    if (!matcher.matches()) {
      // if the regex does not match the imput stream, we just assume the string can be converted to integer directly
      return Integer.valueOf(sampleIdStr);
    }
    else {
      return Integer.valueOf(matcher.group(group));
    }
  }

  public static class SoilSampleRangeProcessor extends AbstractRangeProcessor {
    private DatabaseService databaseService;

    @Override
    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String trialUid = Utilities.getStringCellValue(row.get(0));
      Utilities.validateTrial(databaseService, trialUid);
      String sampleCode = Utilities.getStringCellValue(row.get(1));
      int scode = getSampleId(arguments, sampleCode);
      SoilSample soilSample = databaseService.findSoilSampleById(trialUid, scode);
      if (soilSample == null) {
        soilSample = new SoilSample();
        soilSample.setTrialUniqueId(trialUid);
        soilSample.setSampleId(scode);
      }
      soilSample.setCode(sampleCode);
      try {
        soilSample.setCdate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      soilSample.setTrt(Utilities.getStringCellValue(row.get(3)));
      soilSample.setDepth(Utilities.getStringCellValue(row.get(4)));
      databaseService.createOrUpdateSoilSample(soilSample);
    }
  }

  public static class SoilResultRangeProcessor extends AbstractRangeProcessor {
    private DatabaseService databaseService;

    protected void process(IProcessingContext context, List<Cell> row, IEventCollector eventCollector, SheetRangeConfiguration rangeConfiguration) throws ProcessorException {
      databaseService = context.getDatabaseService();
      String uid = Utilities.getStringCellValue(row.get(0));
      Pair<String, String> pair = Utilities.matchTrialBase(uid);
      if (pair == null) {
        throw new ProcessorException(String.format("Trial soil identifier '%s' is malformed. Please check template", uid));
      }
      String trialUid = pair.car();
      Utilities.validateTrial(databaseService, trialUid);
      String sampleIdStr = pair.cdr();
      int sampleId = getSampleId(arguments, sampleIdStr);
      SoilSample soilSample = databaseService.findSoilSampleById(trialUid, sampleId);
      if (soilSample == null) {
        throw new ProcessorException(String.format("Trial soil sample identified by trial id '%s' and sample id '%d' does not exist.", trialUid, sampleId));
      }
      fillSoilSample(soilSample, row);
      databaseService.updateSoilSample(soilSample);
    }

    private void fillSoilSample(SoilSample soilSample, List<Cell> row) throws ProcessorException {
      soilSample.setSsn(Utilities.getStringCellValue(row.get(1)));
      try {
        soilSample.setAdate(Utilities.getDateCellValue(row.get(2)));
      }
      catch (ParseException e) {
        throw new ProcessorException(e);
      }
      soilSample.setPh(Utilities.getDoubleCellValue(row.get(3)));
      soilSample.setEc(Utilities.getDoubleCellValue(row.get(4)));
      soilSample.setM3ai(Utilities.getDoubleCellValue(row.get(5)));
      soilSample.setM3b(Utilities.getDoubleCellValue(row.get(6)));
      soilSample.setM3ca(Utilities.getDoubleCellValue(row.get(7)));
      soilSample.setM3cu(Utilities.getDoubleCellValue(row.get(8)));
      soilSample.setM3fe(Utilities.getDoubleCellValue(row.get(9)));
      soilSample.setM3k(Utilities.getDoubleCellValue(row.get(10)));
      soilSample.setM3mg(Utilities.getDoubleCellValue(row.get(11)));
      soilSample.setM3mn(Utilities.getDoubleCellValue(row.get(12)));
      soilSample.setM3na(Utilities.getDoubleCellValue(row.get(13)));
      soilSample.setM3p(Utilities.getDoubleCellValue(row.get(14)));
      soilSample.setM3s(Utilities.getDoubleCellValue(row.get(15)));
      soilSample.setM3zn(Utilities.getDoubleCellValue(row.get(16)));
      soilSample.setHp(Utilities.getDoubleCellValue(row.get(17)));
    }

  }
}
