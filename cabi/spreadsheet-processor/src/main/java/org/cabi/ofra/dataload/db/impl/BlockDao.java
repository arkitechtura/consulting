package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IBlockDao;
import org.cabi.ofra.dataload.model.Block;

/**
 * Created by equiros on 05/11/14.
 */
public class BlockDao extends BaseDao implements IBlockDao {
  @Override
  public boolean existsBlock(Block block) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.block WHERE trial_id = ? AND block_id = ?", Integer.class, block.getTrialUniqueId(), String.valueOf(block.getBlockNumber()));
    return count > 0;
  }

  @Override
  public void createBlock(Block block) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.block(trial_id, block_id, block_lati1, block_long1, block_elev, block_lati2, block_long2, " +
                        "                                block_lati3, block_long3, block_lati4, block_long4) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            block.getTrialUniqueId(), String.valueOf(block.getBlockNumber()), block.getLat(), block.getLng(), block.getElevation(),
            block.getLat2(), block.getLng2(), block.getLat3(), block.getLng3(), block.getLat4(), block.getLng4());
  }

  @Override
  public void updateBlock(Block block) {
    jdbcTemplate.update("UPDATE ofrafertrials.block SET block_lati1 = ?, block_long1 = ?, block_elev = ?, block_lati2 = ?, block_long2 = ?, block_lati3 = ?, block_long3 = ?," +
                        "       block_lati4 = ?, block_long4 = ? WHERE trial_id = ? AND block_id = ?",
            block.getLat(), block.getLng(), block.getElevation(), block.getLat2(), block.getLng2(), block.getLat3(), block.getLng3(), block.getLat4(), block.getLng4(),
            block.getTrialUniqueId(), String.valueOf(block.getBlockNumber()));
  }
}
