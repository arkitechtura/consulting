package org.cabi.ofra.dataload.db;

/**
 * Created by equiros on 11/9/2014.
 */
public interface ICropDao extends IDao {
  public boolean existsCrop(String cropId);
}
