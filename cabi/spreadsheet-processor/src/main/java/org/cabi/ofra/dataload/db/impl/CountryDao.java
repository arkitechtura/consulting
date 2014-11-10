package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.ICountryDao;

/**
 * Created by equiros on 11/9/2014.
 */
public class CountryDao extends BaseDao implements ICountryDao {
  @Override
  public boolean existsCountry(String countryCode) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.lkpcountry WHERE country_cod = ?", Integer.class, countryCode);
    return count > 0;
  }
}
