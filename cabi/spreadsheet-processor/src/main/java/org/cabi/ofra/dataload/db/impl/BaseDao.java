package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class BaseDao implements IDao {
  protected JdbcTemplate jdbcTemplate;

  @Override
  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }
}
