package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.ISoilSampleDao;
import org.cabi.ofra.dataload.model.SoilSample;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by equiros on 11/14/2014.
 */
public class SoilSampleDao extends BaseDao implements ISoilSampleDao {
  @Override
  public boolean existsSoilSample(SoilSample sample) {
    return existsSoilSampleById(sample.getTrialUniqueId(), sample.getSampleId());
  }

  @Override
  public boolean existsSoilSampleById(String trialUid, int trialId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.trialsoilsample WHERE trial_id = ? AND ssample_id = ?", Integer.class, trialUid, trialId);
    return count > 0;
  }

  @Override
  public SoilSample findSoilSampleById(String trialUid, int trialId) {
    return jdbcTemplate.queryForObject("SELECT trial_id, ssample_id, ssample_code, ssample_cdate, ssample_trt, ssample_depth, ssample_adate, ssample_ssn, " +
                    "ssample_ph, ssample_ec, ssample_m3ai, ssample_m3b, ssample_m3ca, ssample_m3cu, ssample_m3fe, ssample_m3k, ssample_m3mg, ssample_m3mn, " +
                    "ssample_m3na, ssample_m3p, ssample_m3s, ssample_m3zn, ssmaple_hp " +
                    " FROM ofrafertrials.trialsoilsample " +
                    " WHERE trial_id = ?  AND ssample_id = ?", new Object[] {trialUid, trialId},
            (resultSet, i) -> {
              SoilSample sample = new SoilSample();
              sample.setTrialUniqueId(resultSet.getString(1));
              sample.setSampleId(resultSet.getInt(2));
              sample.setCode(resultSet.getString(3));
              sample.setCdate(resultSet.getDate(4));
              sample.setTrt(resultSet.getString(5));
              sample.setDepth(resultSet.getString(6));
              sample.setAdate(resultSet.getDate(7));
              sample.setSsn(resultSet.getString(8));
              sample.setPh(resultSet.getDouble(9));
              sample.setEc(resultSet.getDouble(10));
              sample.setM3ai(resultSet.getDouble(11));
              sample.setM3b(resultSet.getDouble(12));
              sample.setM3ca(resultSet.getDouble(13));
              sample.setM3cu(resultSet.getDouble(14));
              sample.setM3fe(resultSet.getDouble(15));
              sample.setM3k(resultSet.getDouble(16));
              sample.setM3mg(resultSet.getDouble(17));
              sample.setM3mn(resultSet.getDouble(18));
              sample.setM3na(resultSet.getDouble(19));
              sample.setM3p(resultSet.getDouble(20));
              sample.setM3s(resultSet.getDouble(21));
              sample.setM3zn(resultSet.getDouble(22));
              sample.setHp(resultSet.getDouble(23));
              return sample;
            });
  }

  @Override
  public void createSoilSample(SoilSample sample) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.trialsoilsample (trial_id, ssample_id, ssample_code, ssample_cdate, ssample_trt, ssample_depth, ssample_adate, " +
                        " ssample_ssn, ssample_ph, ssample_ec, ssample_m3ai, ssample_m3b, ssample_m3ca, ssample_m3cu, ssample_m3fe, ssample_m3k, ssample_m3mg, " +
                        " ssample_m3mn, ssample_m3na, ssample_m3p, ssample_m3s, ssample_m3zn, ssmaple_hp) " +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            sample.getTrialUniqueId(), sample.getSampleId(), sample.getCode(), sample.getCdate(), sample.getTrt(), sample.getDepth(), sample.getAdate(),
            sample.getSsn(), sample.getPh(), sample.getEc(), sample.getM3ai(), sample.getM3b(), sample.getM3ca(), sample.getM3cu(), sample.getM3fe(), sample.getM3k(), sample.getM3mg(),
            sample.getM3mn(), sample.getM3na(), sample.getM3p(), sample.getM3s(), sample.getM3zn(), sample.getHp());
  }

  @Override
  public void updateSoilSample(SoilSample sample) {
    jdbcTemplate.update("UPDATE ofrafertrials.trialsoilsample SET ssample_code = ?, ssample_cdate = ?, ssample_trt = ?, ssample_depth = ?, ssample_adate = ?, ssample_ssn = ?, " +
                        " ssample_ph = ?, ssample_ec = ?, ssample_m3ai = ?, ssample_m3b = ?, ssample_m3ca = ?, ssample_m3cu = ?, ssample_m3fe = ?, ssample_m3k = ?, " +
                        " ssample_m3mg = ?, ssample_m3mn = ?, ssample_m3na = ?, ssample_m3p = ?, ssample_m3s = ?, ssample_m3zn = ?, ssmaple_hp = ?" +
                        "  WHERE trial_id = ? AND ssample_id = ?",
            sample.getCode(), sample.getCdate(), sample.getTrt(), sample.getDepth(), sample.getAdate(), sample.getSsn(),
            sample.getPh(), sample.getEc(), sample.getM3ai(), sample.getM3b(), sample.getM3ca(), sample.getM3cu(), sample.getM3fe(), sample.getM3k(),
            sample.getM3mg(), sample.getM3mn(), sample.getM3na(), sample.getM3p(), sample.getM3s(), sample.getM3zn(), sample.getHp(), sample.getTrialUniqueId(),
            sample.getSampleId());
  }
}
