package controller;

import controller.MatchingScoreController.ScoredSpot;
import model.PreferencesModel;
import model.StudySpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingScoreTests {
 
 private MatchingScoreController controller;
 private List<StudySpot> spots;
 
 @BeforeEach
 void setup() {
  StudySpotsController sc = new StudySpotsController();
  spots = sc.getAllStudySpots();
  controller = new MatchingScoreController(spots);
 }
 
 @Test
 void testQuietIndoorPreference() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setNoiseLevel("Silent");
  prefs.setIndoorOutdoor("Indoor");
  prefs.setSpaceType("Library");
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.get(0).getSpot().getNoiseLevel().equalsIgnoreCase("Silent"));
  assertTrue(scored.get(0).getSpot().getIndoorOutdoor().equalsIgnoreCase("Indoor"));
  System.out.println("Test Quiet Indoor: Top = " + scored.get(0).getSpot().getName());
 }
 
 @Test
 void testOpenLateHours() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setOpenTime(LocalTime.of(21, 0));
  prefs.setCloseTime(LocalTime.of(23, 0));
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.stream().anyMatch(s -> s.getSpot().getCloseTime().isAfter(LocalTime.of(22, 0))));
  System.out.println("Test Open Late Hours: Top = " + scored.get(0).getSpot().getName());
 }
 
 
 @Test
 void testAmenityWiFiAndProjector() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setAmenity("WiFi", 1);
  prefs.setAmenity("Projector", 1);
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.get(0).getSpot().getAmenities().get("WiFi") == 1);
  assertTrue(scored.get(0).getSpot().getAmenities().get("Projector") == 1);
 }
 
 @Test
 void testDistanceInfluence() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setMaxDistance(100);
  
  Map<String, Double> distances = Map.of(
   "1", 50.0,
   "2", 100.0,
   "3", 200.0
  );
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, distances);
  assertEquals("1", scored.get(0).getSpot().getId(), "Closest spot should be ranked highest");
 }
 
 @Test
 void testNoiseMatching() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setNoiseLevel("Moderate");
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.get(0).getSpot().getNoiseLevel().equalsIgnoreCase("Moderate")
   || scored.get(0).getSpot().getNoiseLevel().equalsIgnoreCase("Silent"));
 }
 
 @Test
 void testIndoorOutdoorAny() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setIndoorOutdoor("Any");
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertFalse(scored.isEmpty());
 }
 
 @Test
 void testSpaceTypeMatching() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setSpaceType("Group Study Room");
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.get(0).getSpot().getSpaceType().equalsIgnoreCase("Group Study Room"));
 }
 
 
 @Test
 void testPartialAmenities() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setAmenity("WiFi", 1);
  prefs.setAmenity("PrivateRoom", 1);
  prefs.setAmenity("Projector", 1);
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.get(0).getSpot().getAmenities().get("WiFi") == 1);
 }
 
 @Test
 void testNoPreferenceScores() {
  PreferencesModel prefs = new PreferencesModel();
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertFalse(scored.isEmpty(), "Should score all spots even without preferences");
 }
 
 @Test
 void testEdgeOpenHours() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setOpenTime(LocalTime.of(0, 0));
  prefs.setCloseTime(LocalTime.of(23, 59));
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertEquals(100.0, scored.get(0).getScore(), "Top spot should get max score when fully covering preferred hours");
 }
 
 @Test
 void testDistanceAndScoreTieBreaker() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setNoiseLevel("Silent");
  
  Map<String, Double> distances = Map.of(
   "1", 50.0,
   "3", 20.0
  );
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, distances);
  assertEquals("3", scored.get(0).getSpot().getId(), "Closer spot should win tie breaker");
 }
 
 @Test
 void testSpotWithNoAmenities() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setAmenity("WiFi", 1);
  
  // Create a new spot with no amenities
  StudySpot emptySpot = new StudySpot();
  emptySpot.setId("99");
  emptySpot.setName("Empty Spot");
  spots.add(emptySpot);
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  assertTrue(scored.stream().anyMatch(s -> s.getSpot().getId().equals("99")));
 }
 
 @Test
 void testAllPreferencesMetTopScore() {
  PreferencesModel prefs = new PreferencesModel();
  prefs.setNoiseLevel("Silent");
  prefs.setIndoorOutdoor("Indoor");
  prefs.setSpaceType("Library");
  prefs.setAmenity("WiFi", 1);
  prefs.setAmenity("PrivateRoom", 1);
  prefs.setAmenity("Projector", 1);
  
  List<ScoredSpot> scored = controller.calculateScore(prefs, null);
  ScoredSpot top = scored.get(0);
  assertEquals(100.0, top.getScore(), 0.01, "Top spot should reach normalized score of 100");
 }
}
