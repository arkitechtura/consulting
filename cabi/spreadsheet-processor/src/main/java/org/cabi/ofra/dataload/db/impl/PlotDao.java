package org.cabi.ofra.dataload.db.impl;

import org.cabi.ofra.dataload.db.IPlotDao;
import org.cabi.ofra.dataload.model.Plot;

/**
 * Created by equiros on 10/11/14.
 */
public class PlotDao extends BaseDao implements IPlotDao {
  @Override
  public boolean existsPlot(Plot plot) {
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ofrafertrials.plot WHERE trial_id = ? AND block_id = ? AND plot_id = ?", Integer.class,
            plot.getTrialUniqueId(), String.valueOf(plot.getBlockNumber()), plot.getPlotId());
    return count > 0;
  }

  @Override
  public void createPlot(Plot plot) {
    jdbcTemplate.update("INSERT INTO ofrafertrials.plot(trial_id, block_id, plot_id, plot_obs, plot_area, plot_crop1, plot_crop2, plot_crop3, plot_nitrogen," +
                        " plot_phosphorus, plot_potassium, plot_sulphur, plot_zink, plot_magnesium, plot_boron, plot_manure)" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            nvl(plot.getTrialUniqueId()), nvl(String.valueOf(plot.getBlockNumber())), nvl(plot.getPlotId()), nvl(plot.getObservations()), nvl(plot.getArea()), nvl(plot.getCropOne()),
            nvl(plot.getCropTwo()), nvl(plot.getCropThree()), nvl(plot.getNitrogen()), nvl(plot.getPhosphorus()), nvl(plot.getPotassium()), nvl(plot.getSulphur()), nvl(plot.getZinc()),
            nvl(plot.getMagnesium()), nvl(plot.getBoron()), nvl(plot.getManure()));
  }

  @Override
  public void updatePlot(Plot plot) {
    jdbcTemplate.update("UPDATE ofrafertrials.plot SET plot_obs = ?, plot_area = ?, plot_crop1 = ?, plot_crop2 = ?, plot_crop3 = ?, plot_nitrogen = ?," +
                    " plot_phosphorus = ?, plot_potassium = ?, plot_sulphur = ?, plot_zink = ?, plot_magnesium = ?, plot_boron = ?, plot_manure = ?" +
                    " WHERE trial_id = ? AND block_id = ? AND plot_id = ?",
            nvl(plot.getObservations()), nvl(plot.getArea()), nvl(plot.getCropOne()), nvl(plot.getCropTwo()), nvl(plot.getCropThree()), nvl(plot.getNitrogen()),
            nvl(plot.getPhosphorus()), nvl(plot.getPotassium()), nvl(plot.getSulphur()), nvl(plot.getZinc()), nvl(plot.getMagnesium()), nvl(plot.getBoron()),
            nvl(plot.getManure()), nvl(plot.getTrialUniqueId()), nvl(String.valueOf(plot.getBlockNumber())), nvl(plot.getPlotId()));
  }
}
