package org.cabi.ofra.dataload.db;

import org.cabi.ofra.dataload.model.Block;

/**
 * Created by equiros on 05/11/14.
 */
public interface IBlockDao extends IDao {
  public boolean existsBlock(Block block);
  public void createBlock(Block block);
  public void updateBlock(Block block);
}
