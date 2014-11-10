package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.ITrialDao;
import org.cabi.ofra.dataload.model.Trial;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class TrialDao extends BaseDao implements ITrialDao {
  @Override
  public boolean existsTrial(String trialUniqueId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.trial WHERE trial_id = ?", Integer.class, trialUniqueId);
    return count > 0;
  }

  @Override
  public void createTrial(Trial trial) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.trial(trial_id, trial_cnty, trial_region, trial_district, trial_village, trial_frmocentre, trial_agzone, trial_lrserch, trial_fldassi, trial_fldassm, trial_crop1," +
                        "                  trial_crop2, trial_crop3, trial_lati, trial_long, legacy_user) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            trial.getTrialUniqueId(), trial.getCountry(), trial.getRegionCode(), trial.getDistrictCode(), trial.getVillageCode(), trial.getFarmerOrCentre(),
            null, trial.getLeadResearcher(), trial.getFieldAssistantName(), trial.getFieldAssistantTelephone(), trial.getCropOne(),
            trial.getCropTwo(), null, trial.getLat(), trial.getLng(), trial.getUser());
  }

  @Override
  public Trial getTrialById(String trialUniqueId) {
    return jdbcTemplate.queryForObject("select trial_id, trial_cnty, trial_region, trial_district, trial_village, trial_frmocentre, trial_agzone, trial_lrserch, trial_fldassi, trial_fldassm, trial_crop1," +
                                       "       trial_crop2, trial_crop3, trial_lati, trial_long, legacy_user" +
                                       "  from ofrafertrials.trial where trial_id = ?", new Object[] {trialUniqueId},
            (resultSet, i) -> {
              Trial t = new Trial();
              t.setTrialUniqueId(resultSet.getString(1));
              t.setCountry(resultSet.getString(2));
              t.setRegionCode(resultSet.getString(3));
              t.setDistrictCode(resultSet.getString(4));
              t.setVillageCode(resultSet.getString(5));
              t.setFarmerOrCentre(resultSet.getString(6));
              t.setLeadResearcher(resultSet.getString(8));
              t.setFieldAssistantName(resultSet.getString(9));
              t.setFieldAssistantTelephone(resultSet.getString(10));
              t.setCropOne(resultSet.getString(11));
              t.setCropTwo(resultSet.getString(12));
              t.setLat(resultSet.getFloat(14));
              t.setLng(resultSet.getFloat(15));
              t.setUser(resultSet.getString(16));
              return t;
            });
  }

  @Override
  public void updateTrial(Trial trial) {
    jdbcTemplate.update("UPDATE ofrafertrials.trial SET trial_cnty = ?, trial_region = ?, trial_district = ?, trial_village = ?, trial_frmocentre = ?, trial_lrserch = ?," +
                        "                 trial_fldassi = ?, trial_fldassm = ?, trial_crop1 = ?, trial_crop2 = ?, trial_lati = ?, trial_long = ?, " +
                        "                 legacy_user = ? WHERE trial_id = ?",
            trial.getCountry(), trial.getRegionCode(), trial.getDistrictCode(), trial.getVillageCode(), trial.getFarmerOrCentre(), trial.getLeadResearcher(),
            trial.getFieldAssistantName(), trial.getFieldAssistantTelephone(), trial.getCropOne(), trial.getCropTwo(), trial.getLat(), trial.getLng(), trial.getUser(),
            trial.getTrialUniqueId());
  }
}
