package org.cabi.ofra.dataload.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.cabi.ofra.dataload.db.impl.TrialDao;
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
  }

  private void initializeDataSource(String propertiesFile) throws IOException {
    Properties props = Utilities.loadDatabaseProperties(propertiesFile);
    dataSource.setDriverClassName(props.getProperty("database.driver"));
    dataSource.setUrl(props.getProperty("database.url"));
    dataSource.setUsername(props.getProperty("database.username"));
    dataSource.setPassword(props.getProperty("database.password"));
  }

  public void createOrUpdateTrial(Trial t) {
    if (!trialDao.existsTrial(t.getTrialUniqueId())) {
      trialDao.createTrial(t);
    }
    else {
      trialDao.updateTrial(t);
    }
  }
}
