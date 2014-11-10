package org.cabi.ofra.dataload.model;

/**
 * Created by equiros on 11/9/2014.
 */
public class Plot {
  private String trialUniqueId;
  private int blockNumber;
  private int plotId;
  private String observations;
  private double area;
  private String cropOne;
  private String cropTwo;
  private String cropThree;
  private double nitrogen;
  private double phosphorus;
  private double potassium;
  private double sulphur;
  private double zinc;
  private double magnesium;
  private double boron;
  private double manure;

  public String getTrialUniqueId() {
    return trialUniqueId;
  }

  public String getPlotUniqueId() {
    return String.format("%s_B%d_%d", trialUniqueId, blockNumber, plotId);
  }

  public void setTrialUniqueId(String trialUniqueId) {
    this.trialUniqueId = trialUniqueId;
  }

  public int getBlockNumber() {
    return blockNumber;
  }

  public void setBlockNumber(int blockNumber) {
    this.blockNumber = blockNumber;
  }

  public int getPlotId() {
    return plotId;
  }

  public void setPlotId(int plotId) {
    this.plotId = plotId;
  }

  public String getObservations() {
    return observations;
  }

  public void setObservations(String observations) {
    this.observations = observations;
  }

  public double getArea() {
    return area;
  }

  public void setArea(double area) {
    this.area = area;
  }

  public String getCropOne() {
    return cropOne;
  }

  public void setCropOne(String cropOne) {
    this.cropOne = cropOne;
  }

  public String getCropTwo() {
    return cropTwo;
  }

  public void setCropTwo(String cropTwo) {
    this.cropTwo = cropTwo;
  }

  public String getCropThree() {
    return cropThree;
  }

  public void setCropThree(String cropThree) {
    this.cropThree = cropThree;
  }

  public double getNitrogen() {
    return nitrogen;
  }

  public void setNitrogen(double nitrogen) {
    this.nitrogen = nitrogen;
  }

  public double getPhosphorus() {
    return phosphorus;
  }

  public void setPhosphorus(double phosphorus) {
    this.phosphorus = phosphorus;
  }

  public double getPotassium() {
    return potassium;
  }

  public void setPotassium(double potassium) {
    this.potassium = potassium;
  }

  public double getSulphur() {
    return sulphur;
  }

  public void setSulphur(double sulphur) {
    this.sulphur = sulphur;
  }

  public double getZinc() {
    return zinc;
  }

  public void setZinc(double zinc) {
    this.zinc = zinc;
  }

  public double getMagnesium() {
    return magnesium;
  }

  public void setMagnesium(double magnesium) {
    this.magnesium = magnesium;
  }

  public double getBoron() {
    return boron;
  }

  public void setBoron(double boron) {
    this.boron = boron;
  }

  public double getManure() {
    return manure;
  }

  public void setManure(double manure) {
    this.manure = manure;
  }
}
