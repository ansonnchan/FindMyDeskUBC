package controller;

import model.PreferencesModel;
import model.StudySpot;

import java.util.List;
import java.util.Map;

/**
 * Controller class for handling map interactions.
 * Updates map pins based on user preferences and distances, and interacts with DetailsController.
 */
public class MapController {
 
 private final MatchingScoreController scoreController;
 private DetailsController detailsController;
 
 /**
  * Constructs a MapController with the given MatchingScoreController.
  *
  * @param scoreController Controller responsible for calculating matching scores
  */
 public MapController(MatchingScoreController scoreController) {
  this.scoreController = scoreController;
 }
 
 /**
  * Updates the map pins based on user preferences and distances from the user.
  *
  * @param preferences       User preferences model
  * @param distancesFromUser Map of StudySpot IDs to distances from the user in meters
  */
 public void updateMapWithPreferences(PreferencesModel preferences,
                                      Map<String, Double> distancesFromUser) {
  List<MatchingScoreController.ScoredSpot> rankedSpots =
   scoreController.calculateScore(preferences, distancesFromUser);
 }
 
 /**
  * Handles the event when a map pin is clicked.
  * Displays details for the selected StudySpot if a DetailsController is set.
  *
  * @param spot The StudySpot that was clicked
  */
 public void onPinClicked(StudySpot spot) {
  if (detailsController != null) {
   detailsController.showDetails(spot);
  }
 }
 
 /**
  * Sets the DetailsController to allow interaction with detail views.
  *
  * @param detailsController The DetailsController instance to use
  */
 public void setDetailsController(DetailsController detailsController) {
  this.detailsController = detailsController;
 }
 
}
