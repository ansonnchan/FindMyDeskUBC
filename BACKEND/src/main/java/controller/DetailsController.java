package controller;

import model.PreferencesModel;
import model.StudySpot;

/**
 * Controller class for handling details of a selected StudySpot.
 * Generates explanations of how a spot matches user preferences and interacts with MapController.
 */
public class DetailsController {

    private final PreferencesModel preferencesModel;
    private MapController mapController;

    /**
     * Constructs a DetailsController with the given PreferencesModel.
     *
     * @param preferencesModel Model containing user preferences
     */
    public DetailsController(PreferencesModel preferencesModel) {
        this.preferencesModel = preferencesModel;
    }

    /**
     * Generates a textual explanation of how a StudySpot matches the user's preferences.
     *
     * @param selectedSpot the StudySpot to analyze
     * @return Explanation string of match factors, or empty string if selectedSpot is null
     */
    public String showDetails(StudySpot selectedSpot) {
        if (selectedSpot == null) return "";
        return generateMatchExplanation(selectedSpot);
    }

    /**
     * Sets the MapController for this DetailsController.
     *
     * @param mapController the MapController instance to use
     */
    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    /**
     * Generates the detailed explanation string for how the given StudySpot matches preferences.
     *
     * @param spot StudySpot to analyze
     * @return Explanation string describing match factors
     */
    private String generateMatchExplanation(StudySpot spot) {
        StringBuilder sb = new StringBuilder("Match factors:\n");

        if (!preferencesModel.getNoiseLevel().equalsIgnoreCase("Any")) {
            sb.append("- Noise Level: ").append(spot.getNoiseLevel()).append("\n");
        }
        if (!preferencesModel.getIndoorOutdoor().equalsIgnoreCase("Any")) {
            sb.append("- Location Type: ").append(spot.getIndoorOutdoor()).append("\n");
        }
        if (!preferencesModel.getSpaceType().equalsIgnoreCase("Any")) {
            sb.append("- Space Type: ").append(spot.getSpaceType()).append("\n");
        }

        preferencesModel.getAmenities().forEach((amenity, required) -> {
            if (required > 0 && spot.getAmenities().containsKey(amenity)) {
                sb.append("- Has amenity: ").append(amenity).append("\n");
            }
        });

        return sb.toString();
    }
}
