package org.cabi.ofra.dataload.model;

/**
 * Created by equiros on 05/11/14.
 */
public class Block {
  private String trialUniqueId;
  private int blockNumber;
  private double lat;
  private double lng;
  private double elevation;
  private double lat2 = Double.NaN;
  private double lng2 = Double.NaN;;
  private double lat3 = Double.NaN;;
  private double lng3 = Double.NaN;;
  private double lat4 = Double.NaN;;
  private double lng4 = Double.NaN;;

  public String getTrialUniqueId() {
    return trialUniqueId;
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

  public String getBlockUniqueId() {
    return String.format("%s_B%d", trialUniqueId, blockNumber);
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getElevation() {
    return elevation;
  }

  public void setElevation(double elevation) {
    this.elevation = elevation;
  }

  public double getLat2() {
    return lat2;
  }

  public void setLat2(double lat2) {
    this.lat2 = lat2;
  }

  public double getLng2() {
    return lng2;
  }

  public void setLng2(double lng2) {
    this.lng2 = lng2;
  }

  public double getLat3() {
    return lat3;
  }

  public void setLat3(double lat3) {
    this.lat3 = lat3;
  }

  public double getLng3() {
    return lng3;
  }

  public void setLng3(double lng3) {
    this.lng3 = lng3;
  }

  public double getLat4() {
    return lat4;
  }

  public void setLat4(double lat4) {
    this.lat4 = lat4;
  }

  public double getLng4() {
    return lng4;
  }

  public void setLng4(double lng4) {
    this.lng4 = lng4;
  }
}
