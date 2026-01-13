package controller;

import api.ApiIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for walking distance calculations.
 * Provides an endpoint to get distance and duration between two points.
 */
@RestController
@RequestMapping("/api")
public class WalkController {
 
 private final ApiIntegration apiIntegration;
 
 /**
  * Constructs a WalkController with injected ApiIntegration service.
  *
  * @param apiIntegration Service to compute distances
  */
 @Autowired
 public WalkController(ApiIntegration apiIntegration) {
  this.apiIntegration = apiIntegration;
 }
 
 /**
  * Calculates walking distance and duration between two coordinates.
  *
  * @param originLat Latitude of origin
  * @param originLng Longitude of origin
  * @param destLat   Latitude of destination
  * @param destLng   Longitude of destination
  * @return DistanceResponse containing distance in meters and duration in seconds
  */
 @GetMapping("/walk")
 public DistanceResponse getWalkingDistance(
  @RequestParam double originLat,
  @RequestParam double originLng,
  @RequestParam double destLat,
  @RequestParam double destLng
 ) {
  try {
   ApiIntegration.DistanceTime dt =
    apiIntegration.getDistanceTime(originLat, originLng, destLat, destLng);
   return new DistanceResponse(dt.distanceMeters, dt.durationSeconds);
  } catch (ApiIntegration.ApiException e) {
   e.printStackTrace();
   return new DistanceResponse(0, 0);
  } catch (Exception e) {
   e.printStackTrace();
   return new DistanceResponse(0, 0);
  }
 }
 
 /**
  * DTO representing walking distance and duration.
  */
 public static class DistanceResponse {
  public final double distanceMeters;
  public final int durationSeconds;
  
  public DistanceResponse(double distanceMeters, int durationSeconds) {
   this.distanceMeters = distanceMeters;
   this.durationSeconds = durationSeconds;
  }
 }
}
