package org.cabi.ofra.dataload.db;

/**
 * Created by equiros on 11/9/2014.
 */
public interface ICountryDao extends IDao {
  public boolean existsCountry(String countryCode);
}
