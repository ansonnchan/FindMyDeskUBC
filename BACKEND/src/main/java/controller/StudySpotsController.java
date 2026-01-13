package controller;

import model.StudySpot;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for providing study spot data.
 * Supports retrieving all study spots or a specific study spot by ID.
 */
@RestController
@RequestMapping("/api/studyspots")
public class StudySpotsController {
 
 private final List<StudySpot> studySpots;
 
 /**
  * Constructs a StudySpotsController with mock data.
  */
 public StudySpotsController() {
  this.studySpots = initializeMockData();
 }
 
 /**
  * Returns all available study spots.
  *
  * @return List of StudySpot objects
  */
 @GetMapping("/all")
 public List<StudySpot> getAllStudySpots() {
  return studySpots;
 }
 
 /**
  * Returns a study spot by its unique ID.
  *
  * @param id The ID of the study spot
  * @return StudySpot object if found, otherwise null
  */
 @GetMapping("/{id}")
 public StudySpot getStudySpotById(@PathVariable String id) {
  return studySpots.stream()
   .filter(spot -> spot.getId().equals(id))
   .findFirst()
   .orElse(null);
 }
 
 private List<StudySpot> initializeMockData() {
  List<StudySpot> spots = new ArrayList<>();
  
  spots.add(createStudySpot("1", "IKB - Private Study Rooms", "1961 East Mall, Vancouver, BC",
   49.2675, -123.2527, LocalTime.of(6, 0), LocalTime.of(0, 0),
   "Silent", "Indoor", "Group Study Room", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 1, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("2", "IKB - Common Areas", "1961 East Mall, Vancouver, BC",
   49.2676, -123.2528, LocalTime.of(6, 0), LocalTime.of(0, 0),
   "Moderate", "Indoor", "Open Area", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("3", "IKB - Silent Study Rooms", "1961 East Mall, Vancouver, BC",
   49.2677, -123.2529, LocalTime.of(6, 0), LocalTime.of(0, 0),
   "Silent", "Indoor", "Quiet Room", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(
   createStudySpot("4", "Art, Architecture & Music Library (IKB)", "1961 East Mall, Vancouver, BC",
    49.2678, -123.2530, LocalTime.of(6, 0), LocalTime.of(0, 0),
    "Silent", "Indoor", "Library", true,
    Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("5", "Koerner Library", "1958 Main Mall, Vancouver, BC",
   49.2667, -123.2562, LocalTime.of(8, 0), LocalTime.of(20, 0),
   "Silent", "Indoor", "Library", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("6", "Woodward Library", "2198 Health Sciences Mall, Vancouver, BC",
   49.2637, -123.2469, LocalTime.of(9, 0), LocalTime.of(21, 0),
   "Silent", "Indoor", "Library", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("7", "Asian Library", "1871 West Mall, Vancouver, BC",
   49.2694, -123.2585, LocalTime.of(9, 0), LocalTime.of(21, 0),
   "Silent", "Indoor", "Library", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("8", "Allard Law Library", "1822 East Mall, Vancouver, BC",
   49.2685, -123.2520, LocalTime.of(8, 30), LocalTime.of(21, 0),
   "Silent", "Indoor", "Library", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("9", "Allard - Common Areas", "1822 East Mall, Vancouver, BC",
   49.2686, -123.2521, LocalTime.of(8, 30), LocalTime.of(21, 0),
   "Moderate", "Indoor", "Open Area", true,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("10", "Nest - Upper Floors", "6133 University Blvd, Vancouver, BC",
   49.2668, -123.2500, LocalTime.of(6, 0), LocalTime.of(23, 0),
   "Loud", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("11", "Nest - The Egg", "6133 University Blvd, Vancouver, BC",
   49.2669, -123.2501, LocalTime.of(6, 0), LocalTime.of(23, 0),
   "Loud", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(
   createStudySpot("12", "Life Sciences - Upper Floors", "2350 Health Sciences Mall, Vancouver, BC",
    49.2634, -123.2477, LocalTime.of(8, 0), LocalTime.of(21, 0),
    "Moderate", "Indoor", "Open Area", false,
    Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(
   createStudySpot("13", "Life Sciences - Lower Floors", "2350 Health Sciences Mall, Vancouver, BC",
    49.2635, -123.2478, LocalTime.of(8, 0), LocalTime.of(21, 0),
    "Moderate", "Indoor", "Open Area", false,
    Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("14", "Life Sciences - Special Classrooms",
   "2350 Health Sciences Mall, Vancouver, BC",
   49.2636, -123.2479, LocalTime.of(8, 0), LocalTime.of(21, 0),
   "Silent", "Indoor", "Quiet Room", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 1, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("15", "Hebb - Physics Drop-In Center (1st Floor)",
   "2045 East Mall, Vancouver, BC",
   49.2662, -123.2520, LocalTime.of(7, 30), LocalTime.of(17, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("16", "Hebb - Basement Study Area", "2045 East Mall, Vancouver, BC",
   49.2663, -123.2521, LocalTime.of(7, 30), LocalTime.of(17, 0),
   "Silent", "Indoor", "Quiet Room", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("17", "Education Building - Classrooms (Downstairs)",
   "2125 Main Mall, Vancouver, BC",
   49.2640, -123.2551, LocalTime.of(8, 0), LocalTime.of(20, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 1, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("18", "Arts Undergraduate Center", "1860 East Mall, Vancouver, BC",
   49.2695, -123.2558, LocalTime.of(8, 0), LocalTime.of(21, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(
   createStudySpot("19", "Buchanan - 3rd Floor Study Spot", "1866 Main Mall, Vancouver, BC",
    49.2693, -123.2540, LocalTime.of(7, 30), LocalTime.of(20, 0),
    "Moderate", "Indoor", "Open Area", false,
    Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("20", "Buchanan - 2nd Floor Couches (Above Cafe)",
   "1866 Main Mall, Vancouver, BC",
   49.2694, -123.2541, LocalTime.of(7, 30), LocalTime.of(20, 0),
   "Loud", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("21", "Alumni Center", "6163 University Blvd, Vancouver, BC",
   49.2670, -123.2513, LocalTime.of(8, 0), LocalTime.of(18, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("22", "Hennings Building", "6224 Agricultural Rd, Vancouver, BC",
   49.2665, -123.2527, LocalTime.of(7, 30), LocalTime.of(21, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("23", "CIRS Building", "2260 West Mall, Vancouver, BC",
   49.2646, -123.2560, LocalTime.of(7, 30), LocalTime.of(19, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("24", "ESB (Earth Sciences Building)", "2207 Main Mall, Vancouver, BC",
   49.2627, -123.2526, LocalTime.of(8, 0), LocalTime.of(20, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("25", "Sauder - Lower Level", "2053 Main Mall, Vancouver, BC",
   49.2653, -123.2541, LocalTime.of(7, 0), LocalTime.of(22, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("26", "Sauder - Glass Rooms", "2053 Main Mall, Vancouver, BC",
   49.2654, -123.2542, LocalTime.of(7, 0), LocalTime.of(22, 0),
   "Silent", "Indoor", "Group Study Room", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 1, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("27", "Sauder - Main Floor Large Room", "2053 Main Mall, Vancouver, BC",
   49.2655, -123.2543, LocalTime.of(7, 0), LocalTime.of(22, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("28", "ICICS (Computer Science)", "2366 Main Mall, Vancouver, BC",
   49.2612, -123.2489, LocalTime.of(8, 0), LocalTime.of(22, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("29", "ICICS - Library", "2366 Main Mall, Vancouver, BC",
   49.2613, -123.2490, LocalTime.of(8, 0), LocalTime.of(22, 0),
   "Silent", "Indoor", "Library", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("30", "ICICS - Undergraduate Labs", "2366 Main Mall, Vancouver, BC",
   49.2614, -123.2491, LocalTime.of(8, 0), LocalTime.of(22, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("31", "ICICS - Private Study Rooms", "2366 Main Mall, Vancouver, BC",
   49.2615, -123.2492, LocalTime.of(8, 0), LocalTime.of(22, 0),
   "Silent", "Indoor", "Group Study Room", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 1, "Projector", 1, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("32", "MacLeod Building", "2356 Main Mall, Vancouver, BC",
   49.2614, -123.2502, LocalTime.of(8, 0), LocalTime.of(20, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 0)));
  
  spots.add(createStudySpot("33", "English Language Institute", "2121 West Mall, Vancouver, BC",
   49.2698, -123.2591, LocalTime.of(8, 0), LocalTime.of(20, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  spots.add(createStudySpot("34", "Kaiser Building", "2332 Main Mall, Vancouver, BC",
   49.2623, -123.2497, LocalTime.of(7, 0), LocalTime.of(18, 0),
   "Moderate", "Indoor", "Open Area", false,
   Map.of("WiFi", 1, "Washrooms", 1, "PrivateRoom", 0, "Projector", 0, "FoodOrDrinks", 1)));
  
  return spots;
 }
 
 private StudySpot createStudySpot(String id, String name, String address,
                                   double lat, double lng,
                                   LocalTime open, LocalTime close,
                                   String noise, String indoorOutdoor, String spaceType,
                                   boolean access, Map<String, Integer> amenities) {
  StudySpot spot = new StudySpot();
  spot.setId(id);
  spot.setName(name);
  spot.setLatitude(lat);
  spot.setLongitude(lng);
  spot.setOpenTime(open);
  spot.setCloseTime(close);
  spot.setNoiseLevel(noise);
  spot.setIndoorOutdoor(indoorOutdoor);
  spot.setSpaceType(spaceType);
  spot.setAccessRequired(access);
  spot.setAmenities(new HashMap<>(amenities));
  return spot;
 }
}
