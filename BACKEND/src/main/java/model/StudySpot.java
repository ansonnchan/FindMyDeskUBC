package model;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents a study spot on campus.
 * Each study spot contains information about location, noise level, type of space,
 * accessibility, opening hours, amenities, and photos.
 *
 */
public class StudySpot {
 
 private String id;
 private String name;
 private double latitude;
 private double longitude;
 @JsonFormat(pattern = "HH:mm")
 private LocalTime openTime = LocalTime.MIN;
 @JsonFormat(pattern = "HH:mm")
 private LocalTime closeTime = LocalTime.MAX;
 private String noiseLevel;
 private String indoorOutdoor;
 private String spaceType;
 private boolean accessRequired;
 private Map<String, Integer> amenities = new HashMap<>();
 
 
 public String getId() {
  return id;
 }
 
 public String getName() {
  return name;
 }
 
 public double getLatitude() {
  return latitude;
 }
 
 public double getLongitude() {
  return longitude;
 }
 
 public LocalTime getOpenTime() {
  return openTime;
 }
 
 public LocalTime getCloseTime() {
  return closeTime;
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
 
 public boolean isAccessRequired() {
  return accessRequired;
 }
 
 public Map<String, Integer> getAmenities() {
  return Collections.unmodifiableMap(amenities);
 }
 
 
 public void setId(String id) {
  this.id = id;
 }
 
 public void setName(String name) {
  this.name = name;
 }
 
 public void setLatitude(double latitude) {
  this.latitude = latitude;
 }
 
 public void setLongitude(double longitude) {
  this.longitude = longitude;
 }
 
 public void setOpenTime(LocalTime openTime) {
  this.openTime = openTime;
 }
 
 public void setCloseTime(LocalTime closeTime) {
  this.closeTime = closeTime;
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
 
 public void setAmenities(Map<String, Integer> amenities) {
  this.amenities = new HashMap<>(amenities);
 }
 
 /**
  * Returns a string representation of the StudySpot object.
  * Includes key details: id, name, noise level, type of space, amenities,
  * access requirement, and opening/closing times.
  *
  * @return a string containing the StudySpot's properties in a readable format
  */
 @Override
 public String toString() {
  return "StudySpot{" +
   "id='" + id + '\'' +
   ", name='" + name + '\'' +
   ", noiseLevel='" + noiseLevel + '\'' +
   ", indoorOutdoor='" + indoorOutdoor + '\'' +
   ", spaceType='" + spaceType + '\'' +
   ", amenities=" + amenities +
   ", accessRequired=" + accessRequired +
   ", openTime=" + openTime +
   ", closeTime=" + closeTime +
   '}';
 }
}
