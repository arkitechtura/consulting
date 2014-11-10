package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.ICropDao;

/**
 * Created by equiros on 11/9/2014.
 */
public class CropDao extends BaseDao implements ICropDao {
  @Override
  public boolean existsCrop(String cropId) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.lkpcrop WHERE crop_id = ?", Integer.class, cropId);
    return count > 0;
  }
}
