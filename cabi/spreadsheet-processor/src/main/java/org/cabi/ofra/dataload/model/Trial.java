package org.cabi.ofra.dataload.model;

import org.cabi.ofra.dataload.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class Trial {
  private String trialUniqueId;
  private String country;
  private String regionName;
  private String regionCode;
  private String districtName;
  private String districtCode;
  private String villageName;
  private String villageCode;
  private String cropOne;
  private String cropTwo;
  private int year;
  private String season;
  private String farmerOrCentre;
  private String leadResearcher;
  private String fieldAssistantName;
  private String fieldAssistantTelephone;
  private double lat;
  private double lng;
  private String user;

  public String getTrialUniqueId() {
    return trialUniqueId;
  }

  public void setTrialUniqueId(String trialUniqueId) {
    this.trialUniqueId = trialUniqueId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getDistrictName() {
    return districtName;
  }

  public void setDistrictName(String districtName) {
    this.districtName = districtName;
  }

  public String getDistrictCode() {
    return districtCode;
  }

  public void setDistrictCode(String districtCode) {
    this.districtCode = districtCode;
  }

  public String getVillageName() {
    return villageName;
  }

  public void setVillageName(String villageName) {
    this.villageName = villageName;
  }

  public String getVillageCode() {
    return villageCode;
  }

  public void setVillageCode(String villageCode) {
    this.villageCode = villageCode;
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

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getSeason() {
    return season;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public String getFarmerOrCentre() {
    return farmerOrCentre;
  }

  public void setFarmerOrCentre(String farmerOrCentre) {
    this.farmerOrCentre = farmerOrCentre;
  }

  public String getLeadResearcher() {
    return leadResearcher;
  }

  public void setLeadResearcher(String leadResearcher) {
    this.leadResearcher = leadResearcher;
  }

  public String getFieldAssistantName() {
    return fieldAssistantName;
  }

  public void setFieldAssistantName(String fieldAssistantName) {
    this.fieldAssistantName = fieldAssistantName;
  }

  public String getFieldAssistantTelephone() {
    return fieldAssistantTelephone;
  }

  public void setFieldAssistantTelephone(String fieldAssistantTelephone) {
    this.fieldAssistantTelephone = fieldAssistantTelephone;
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

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Pair<Boolean, String> validate() {
    boolean ret = true;
    String msg = null;
    if (country == null) {
      ret = false;
      msg = String.format("Country can not be null on trial unique id '%s'", trialUniqueId);
    }
    else if (regionCode == null) {
      ret = false;
      msg = String.format("Region can not be null on trial unique id '%s'", trialUniqueId);
    }
    else if (districtCode == null) {
      ret = false;
      msg = String.format("District can not be null on trial unique id '%s'", trialUniqueId);
    }
    else if (villageCode == null) {
      ret = false;
      msg = String.format("Village can not be null on trial unique id '%s'", trialUniqueId);
    }
    else if (cropOne == null) {
      ret = false;
      msg = String.format("At least one crop must be specified for trial unique id '%s'", trialUniqueId);
    }
    return new Pair<>(ret, msg);
  }
}
