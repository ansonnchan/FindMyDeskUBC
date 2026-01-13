package controller;

import model.PreferencesModel;
import model.StudySpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * REST controller for managing user preferences for study spots.
 * Handles applying and resetting preferences and mapping frontend input to backend models.
 */
@RestController
@RequestMapping("/api/preferences")
public class PreferencesController {

    private final PreferencesModel preferencesModel;
    private final MatchingScoreController scoreCalculator;
    private final StudySpotsController studySpotsController;

    /**
     * Constructs a PreferencesController.
     *
     * @param scoreCalculator MatchingScoreController used to calculate scores
     * @param studySpotsController Controller providing access to all study spots
     */
    @Autowired
    public PreferencesController(MatchingScoreController scoreCalculator,
                                 StudySpotsController studySpotsController) {
        this.preferencesModel = new PreferencesModel();
        this.scoreCalculator = scoreCalculator;
        this.studySpotsController = studySpotsController;
    }

   /**
 * Applies user preferences to filter and rank study spots.
 *
 * @param request PreferencesRequest containing user-selected options
 * @return List of ScoredSpot objects based on the applied preferences
 */
@PostMapping("/apply")
public List<MatchingScoreController.ScoredSpot> applyPreferences(
        @RequestBody PreferencesRequest request) {

    if (request.isShowAll()) {
        List<MatchingScoreController.ScoredSpot> allSpots = new ArrayList<>();
        for (StudySpot spot : studySpotsController.getAllStudySpots()) {
            allSpots.add(new MatchingScoreController.ScoredSpot(spot, 100.0));
        }
        return allSpots;
    }

    preferencesModel.setNoiseLevel(request.getNoiseLevel());
    preferencesModel.setIndoorOutdoor(request.getIndoorOutdoor());
    preferencesModel.setAccessRequired(request.isAccessRequired());
    preferencesModel.setMaxDistance(request.getMaxDistance());
    preferencesModel.setOpenTime(LocalTime.parse(request.getOpenTime()));
    preferencesModel.setCloseTime(LocalTime.parse(request.getCloseTime()));

    if (request.getSelectedSpaceTypes() != null && !request.getSelectedSpaceTypes().isEmpty()) {
        String mappedType = mapSpaceTypeKey(request.getSelectedSpaceTypes().get(0));
        preferencesModel.setSpaceType(mappedType);
    } else {
        preferencesModel.setSpaceType("Any");
    }

    for (String key : preferencesModel.getAmenities().keySet()) {
        preferencesModel.setAmenity(key, 0);
    }

    if (request.getSelectedAmenities() != null) {
        for (String amenity : request.getSelectedAmenities()) {
            String mappedKey = mapAmenityKey(amenity);
            preferencesModel.setAmenity(mappedKey, 1);
        }
    }

    return scoreCalculator.calculateScore(preferencesModel, request.getDistancesFromUser());
}


    /**
     * Resets all preferences to default values.
     *
     * @return Map indicating success of the reset operation
     */
    @PostMapping("/reset")
    public Map<String, String> resetPreferences() {
        preferencesModel.reset();
        return Collections.singletonMap("status", "success");
    }

    private String mapAmenityKey(String frontendKey) {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("wifi", "WiFi");
        mapping.put("washrooms", "Washrooms");
        mapping.put("food_drink_allowed", "FoodOrDrinks");
        mapping.put("private_rooms", "PrivateRoom");
        mapping.put("projectors", "Projector");
        return mapping.getOrDefault(frontendKey, frontendKey);
    }

    private String mapSpaceTypeKey(String frontendKey) {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("open_area", "Open Area");
        mapping.put("quiet_room", "Quiet Room");
        mapping.put("group_study", "Group Study Room");
        mapping.put("outdoor_seating", "Outdoor Seating");
        mapping.put("library_classroom", "Library");
        return mapping.getOrDefault(frontendKey, frontendKey);
    }

    /**
     * DTO representing a preferences request from the frontend.
     */
    public static class PreferencesRequest {
        private boolean showAll;
        private String noiseLevel;
        private String indoorOutdoor;
        private List<String> selectedAmenities;
        private List<String> selectedSpaceTypes;
        private boolean accessRequired;
        private int maxDistance;
        private String openTime;
        private String closeTime;
        private Map<String, Double> distancesFromUser;

        public boolean isShowAll() {
            return showAll;
        }
        public void setShowAll(boolean showAll) {
            this.showAll = showAll;
        }
        public String getNoiseLevel() {
            return noiseLevel;
        }
        public void setNoiseLevel(String noiseLevel) {
            this.noiseLevel = noiseLevel;
        }
        public String getIndoorOutdoor() {
            return indoorOutdoor;
        }
        public void setIndoorOutdoor(String indoorOutdoor) {
            this.indoorOutdoor = indoorOutdoor;
        }
        public List<String> getSelectedAmenities() {
            return selectedAmenities;
        }
        public void setSelectedAmenities(List<String> selectedAmenities) {
            this.selectedAmenities = selectedAmenities;
        }
        public List<String> getSelectedSpaceTypes() {
            return selectedSpaceTypes;
        }
        public void setSelectedSpaceTypes(List<String> selectedSpaceTypes) {
            this.selectedSpaceTypes = selectedSpaceTypes;
        }
        public boolean isAccessRequired() {
            return accessRequired;
        }
        public void setAccessRequired(boolean accessRequired) {
            this.accessRequired = accessRequired;
        }
        public int getMaxDistance() {
            return maxDistance;
        }
        public void setMaxDistance(int maxDistance) {
             this.maxDistance = maxDistance;
             }
        public String getOpenTime() {
            return openTime;
         }
        public void setOpenTime(String openTime) {
            this.openTime = openTime;
         }
        public String getCloseTime() {
            return closeTime;
         }
        public void setCloseTime(String closeTime) {
             this.closeTime = closeTime;
            }
        public Map<String, Double> getDistancesFromUser() {
            return distancesFromUser;
        }
        public void setDistancesFromUser(Map<String, Double> distancesFromUser) {
             this.distancesFromUser = distancesFromUser;
             }
    }
}
