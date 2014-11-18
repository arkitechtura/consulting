package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.SoilSample;

/**
 * Created by equiros on 11/14/2014.
 */
public interface ISoilSampleDao extends IDao {
  public boolean existsSoilSample(SoilSample sample);
  public boolean existsSoilSampleById(String trialUid, int trialId);
  public SoilSample findSoilSampleById(String trialUid, int trialId);
  public void createSoilSample(SoilSample sample);
  public void updateSoilSample(SoilSample sample);
}
