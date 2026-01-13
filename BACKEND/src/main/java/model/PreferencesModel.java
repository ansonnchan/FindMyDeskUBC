package model;

import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Model to store user preferences for study spots.
 */
public class PreferencesModel {
 
 private static final String DEFAULT_NOISE = "Any";
 private static final String DEFAULT_INDOOR_OUTDOOR = "Any";
 private static final String DEFAULT_SPACE_TYPE = "Any";
 private static final boolean DEFAULT_ACCESS_REQUIRED = false;
 private static final int DEFAULT_MAX_DISTANCE = Integer.MAX_VALUE;
 private static final LocalTime DEFAULT_OPEN_TIME = LocalTime.MIN;
 private static final LocalTime DEFAULT_CLOSE_TIME = LocalTime.MAX;
 
 private String noiseLevel;
 private String indoorOutdoor;
 private String spaceType;
 private final Map<String, Integer> amenities;
 private boolean accessRequired;
 private int maxDistance;
 private LocalTime openTime;
 private LocalTime closeTime;
 
 public PreferencesModel() {
  this.noiseLevel = DEFAULT_NOISE;
  this.indoorOutdoor = DEFAULT_INDOOR_OUTDOOR;
  this.spaceType = DEFAULT_SPACE_TYPE;
  this.accessRequired = DEFAULT_ACCESS_REQUIRED;
  this.maxDistance = DEFAULT_MAX_DISTANCE;
  this.openTime = DEFAULT_OPEN_TIME;
  this.closeTime = DEFAULT_CLOSE_TIME;
  
  this.amenities = new HashMap<>();
  amenities.put("WiFi", 0);
  amenities.put("Washrooms", 0);
  amenities.put("PrivateRoom", 0);
  amenities.put("Projector", 0);
  amenities.put("FoodOrDrinks", 0);
 }
 
 public String getNoiseLevel() {
  return noiseLevel;
 }
 
 public String getIndoorOutdoor() {
  return indoorOutdoor;
 }
 
 public String getSpaceType() {
  return spaceType;
 }
 
 public Map<String, Integer> getAmenities() {
  return Collections.unmodifiableMap(amenities);
 }
 
 public boolean isAccessRequired() {
  return accessRequired;
 }
 
 public int getMaxDistance() {
  return maxDistance;
 }
 
 public LocalTime getOpenTime() {
  return openTime;
 }
 
 public LocalTime getCloseTime() {
  return closeTime;
 }
 
 
 public void setNoiseLevel(String noiseLevel) {
  this.noiseLevel = noiseLevel;
 }
 
 public void setIndoorOutdoor(String indoorOutdoor) {
  this.indoorOutdoor = indoorOutdoor;
 }
 
 public void setSpaceType(String spaceType) {
  this.spaceType = spaceType;
 }
 
 public void setAccessRequired(boolean accessRequired) {
  this.accessRequired = accessRequired;
 }
 
 public void setMaxDistance(int maxDistance) {
  this.maxDistance = maxDistance;
 }
 
 public void setOpenTime(LocalTime openTime) {
  this.openTime = openTime;
 }
 
 public void setCloseTime(LocalTime closeTime) {
  this.closeTime = closeTime;
 }
 
 /**
  * Updates the value of a single amenity.
  *
  * @param key   amenity name
  * @param value 0 or 1
  */
 public void setAmenity(String key, int value) {
  if (amenities.containsKey(key)) {
   amenities.put(key, value);
  }
 }
 
 /**
  * Resets all preferences to default values.
  */
 public void reset() {
  this.noiseLevel = DEFAULT_NOISE;
  this.indoorOutdoor = DEFAULT_INDOOR_OUTDOOR;
  this.spaceType = DEFAULT_SPACE_TYPE;
  this.accessRequired = DEFAULT_ACCESS_REQUIRED;
  this.maxDistance = DEFAULT_MAX_DISTANCE;
  this.openTime = DEFAULT_OPEN_TIME;
  this.closeTime = DEFAULT_CLOSE_TIME;
  this.amenities.replaceAll((k, v) -> 0);
 }
}
