package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.Trial;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public interface ITrialDao extends IDao {
  public void createTrial(Trial trial);
  public Trial getTrialById(String trialUniqueId);
  public void updateTrial(Trial trial);
  public boolean existsTrial(String trialUniqueId);
}
