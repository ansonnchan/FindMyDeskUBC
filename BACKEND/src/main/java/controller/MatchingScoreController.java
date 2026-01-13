package controller;

import model.PreferencesModel;
import model.StudySpot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for calculating matching scores for StudySpot instances.
 * Scores are based on user preferences, distance, amenities, noise level, space type, indoor/outdoor,
 * accessibility, and operating hours.
 */
public class MatchingScoreController {
 
 private static final double DISTANCE_WEIGHT = 20.0;
 private static final double AMENITIES_WEIGHT = 20.0;
 private static final double NOISE_WEIGHT = 15.0;
 private static final double SPACE_TYPE_WEIGHT = 15.0;
 private static final double INDOOR_OUTDOOR_WEIGHT = 10.0;
 private static final double ACCESS_WEIGHT = 10.0;
 private static final double HOURS_WEIGHT = 10.0;
 
 private final List<StudySpot> studySpots;
 
 public MatchingScoreController(List<StudySpot> studySpots) {
  this.studySpots = studySpots;
 }
 
 public List<StudySpot> getStudySpots() {
  return studySpots;
 }
 
 public List<ScoredSpot> calculateScore(PreferencesModel preferences, Map<String, Double> distancesFromUser) {
  List<ScoredSpot> scoredList = new ArrayList<>();
  
  for (StudySpot spot : studySpots) {
   double score = 0;
   double distance = distancesFromUser != null ?
    distancesFromUser.getOrDefault(spot.getId(), Double.MAX_VALUE) :
    Double.MAX_VALUE;
   
   if (distancesFromUser != null && distancesFromUser.containsKey(spot.getId())
    && preferences.getMaxDistance() > 0) {
    double ratio = distance / preferences.getMaxDistance();
    ratio = Math.min(1.0, Math.max(0.0, ratio));
    score += DISTANCE_WEIGHT * (1 - ratio);
   }
   
   Map<String, Integer> userAm = preferences.getAmenities();
   Map<String, Integer> spotAm = spot.getAmenities();
   int n = userAm.size();
   double perAmenityWeight = (n > 0) ? AMENITIES_WEIGHT / n : 0;
   for (String key : userAm.keySet()) {
    int want = userAm.get(key);
    int has = spotAm.getOrDefault(key, 0);
    if (want == 1 && has == 1) score += perAmenityWeight;
   }
   
   score += calculateNoiseScore(spot.getNoiseLevel(), preferences.getNoiseLevel());
   
   if (!preferences.getSpaceType().equalsIgnoreCase("Any")
    && preferences.getSpaceType().equalsIgnoreCase(spot.getSpaceType())) {
    score += SPACE_TYPE_WEIGHT;
   }
   
   if (!preferences.getIndoorOutdoor().equalsIgnoreCase("Any")
    && preferences.getIndoorOutdoor().equalsIgnoreCase(spot.getIndoorOutdoor())) {
    score += INDOOR_OUTDOOR_WEIGHT;
   }
   
   if (!preferences.isAccessRequired() || spot.isAccessRequired()) {
    score += ACCESS_WEIGHT;
   }
   
   score += calculateHoursScore(spot.getOpenTime(), spot.getCloseTime(),
    preferences.getOpenTime(), preferences.getCloseTime());
   
   ScoredSpot scoredSpot = new ScoredSpot(spot, score);
   scoredSpot.setDistance(distance);
   scoredList.add(scoredSpot);
  }
  
  normalizeScores(scoredList);
  
  scoredList.sort((a, b) -> {
   int cmp = Double.compare(b.getScore(), a.getScore());
   if (cmp != 0) return cmp;
   return Double.compare(a.getDistance(), b.getDistance());
  });
  
  return scoredList;
 }
 
 private double calculateNoiseScore(String spotNoise, String prefNoise) {
  if (prefNoise.equalsIgnoreCase("Any")) return NOISE_WEIGHT;
  if (spotNoise.equalsIgnoreCase(prefNoise)) return NOISE_WEIGHT;
  
  boolean adjacent =
   (spotNoise.equalsIgnoreCase("Silent") && prefNoise.equalsIgnoreCase("Moderate")) ||
    (spotNoise.equalsIgnoreCase("Moderate") && prefNoise.equalsIgnoreCase("Silent")) ||
    (spotNoise.equalsIgnoreCase("Moderate") && prefNoise.equalsIgnoreCase("Loud")) ||
    (spotNoise.equalsIgnoreCase("Loud") && prefNoise.equalsIgnoreCase("Moderate"));
  
  return adjacent ? NOISE_WEIGHT / 2 : 0;
 }
 
 private double calculateHoursScore(LocalTime spotOpen, LocalTime spotClose,
                                    LocalTime prefOpen, LocalTime prefClose) {
  if ((spotOpen.isBefore(prefOpen) || spotOpen.equals(prefOpen)) &&
   (spotClose.isAfter(prefClose) || spotClose.equals(prefClose))) {
   return HOURS_WEIGHT;
  }
  boolean overlaps = spotClose.isAfter(prefOpen) && spotOpen.isBefore(prefClose);
  return overlaps ? HOURS_WEIGHT / 2 : 0;
 }
 
 private void normalizeScores(List<ScoredSpot> scoredList) {
  if (scoredList.isEmpty()) return;
  double max = scoredList.stream().mapToDouble(ScoredSpot::getScore).max().orElse(1);
  double min = scoredList.stream().mapToDouble(ScoredSpot::getScore).min().orElse(0);
  double range = (max - min == 0) ? 1 : (max - min);
  for (ScoredSpot s : scoredList) {
   s.setScore((s.getScore() - min) / range * 100.0);
  }
 }
 
 /**
  * DTO for frontend: includes coordinates, name, score, and optional type info.
  */
 public static class ScoredSpot {
  private final String id;
  private final String name;
  private final double latitude;
  private final double longitude;
  private final String spaceType;
  private final String indoorOutdoor;
  private final String noiseLevel;
  private final LocalTime openTime;
  private final LocalTime closeTime;
  private final boolean accessRequired;
  private double score;
  private double distance;
  private final StudySpot spot;
  
  public ScoredSpot(StudySpot spot, double score) {
   this.spot = spot;
   this.id = spot.getId();
   this.name = spot.getName();
   this.latitude = spot.getLatitude();
   this.longitude = spot.getLongitude();
   this.spaceType = spot.getSpaceType();
   this.indoorOutdoor = spot.getIndoorOutdoor();
   this.noiseLevel = spot.getNoiseLevel();
   this.openTime = spot.getOpenTime();
   this.closeTime = spot.getCloseTime();
   this.accessRequired = spot.isAccessRequired();
   this.score = score;
   this.distance = 0;
  }
  
  public String getId() { return id; }
  public String getName() { return name; }
  public double getLatitude() { return latitude; }
  public double getLongitude() { return longitude; }
  public String getSpaceType() { return spaceType; }
  public String getIndoorOutdoor() { return indoorOutdoor; }
  public String getNoiseLevel() { return noiseLevel; }
  public LocalTime getOpenTime() { return openTime; }
  public LocalTime getCloseTime() { return closeTime; }
  public boolean isAccessRequired() { return accessRequired; }
  public double getScore() { return score; }
  public double getDistance() { return distance; }
  public StudySpot getSpot() { return spot; }
  
  public void setScore(double score) { this.score = score; }
  public void setDistance(double distance) { this.distance = distance; }
 }
}
