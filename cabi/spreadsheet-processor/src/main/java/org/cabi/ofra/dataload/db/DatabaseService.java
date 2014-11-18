package org.cabi.ofra.dataload.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.cabi.ofra.dataload.db.impl.*;
import org.cabi.ofra.dataload.model.Block;
import org.cabi.ofra.dataload.model.Plot;
import org.cabi.ofra.dataload.model.SoilSample;
import org.cabi.ofra.dataload.model.Trial;
import org.cabi.ofra.dataload.util.Utilities;

import java.io.IOException;
import java.util.Properties;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class DatabaseService {
  private BasicDataSource dataSource;
  private ITrialDao trialDao;
  private IBlockDao blockDao;
  private ICropDao cropDao;
  private ICountryDao countryDao;
  private IPlotDao plotDao;
  private ISoilSampleDao soilSampleDao;

  public DatabaseService() {
    dataSource = new BasicDataSource();
  }

  public void initialize(String propertiesFile) throws IOException {
    initializeDataSource(propertiesFile);
    initializeDaos();
  }

  private void initializeDaos() {
    trialDao = new TrialDao();
    trialDao.setDataSource(dataSource);
    blockDao = new BlockDao();
    blockDao.setDataSource(dataSource);
    cropDao = new CropDao();
    cropDao.setDataSource(dataSource);
    countryDao = new CountryDao();
    countryDao.setDataSource(dataSource);
    plotDao = new PlotDao();
    plotDao.setDataSource(dataSource);
    soilSampleDao = new SoilSampleDao();
    soilSampleDao.setDataSource(dataSource);
  }

  private void initializeDataSource(String propertiesFile) throws IOException {
    Properties props = Utilities.loadDatabaseProperties(propertiesFile);
    dataSource.setDriverClassName(props.getProperty("database.driver"));
    dataSource.setUrl(props.getProperty("database.url"));
    dataSource.setUsername(props.getProperty("database.username"));
    dataSource.setPassword(props.getProperty("database.password"));
  }

  public boolean existsTrialByUniqueId(String uid) {
    return trialDao.existsTrial(uid);
  }

  public void createOrUpdateTrial(Trial t) {
    if (!trialDao.existsTrial(t.getTrialUniqueId())) {
      trialDao.createTrial(t);
    }
    else {
      trialDao.updateTrial(t);
    }
  }

  public void createOrUpdateBlock(Block b) {
    if (!blockDao.existsBlock(b)) {
      blockDao.createBlock(b);
    }
    else {
      blockDao.updateBlock(b);
    }
  }

  public boolean existsCrop(String cropId) {
    return cropDao.existsCrop(cropId);
  }

  public boolean existsCountry(String countryCode) {
    return countryDao.existsCountry(countryCode);
  }

  public void createOrUpdatePlot(Plot p) {
    if (!plotDao.existsPlot(p)) {
      plotDao.createPlot(p);
    }
    else {
      plotDao.updatePlot(p);
    }
  }

  public boolean existsSoilSample(String trialUid, int trialId) {
    return soilSampleDao.existsSoilSampleById(trialUid, trialId);
  }

  public SoilSample findSoilSampleById(String trialUid, int trialId) {
    return soilSampleDao.findSoilSampleById(trialUid, trialId);
  }

  public void updateSoilSample(SoilSample soilSample) {
    soilSampleDao.updateSoilSample(soilSample);
  }

  public void createOrUpdateSoilSample(SoilSample soilSample) {
    if (soilSampleDao.existsSoilSample(soilSample)) {
      soilSampleDao.updateSoilSample(soilSample);
    }
    else {
      soilSampleDao.createSoilSample(soilSample);
    }
  }
}
