package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Service class for integrating with external APIs for geocoding and routing.
 * Uses Nominatim for address-to-coordinate geocoding and OSRM for walking route calculations.
 */
@Service
public class ApiIntegration {
 
 private static final Logger logger = LoggerFactory.getLogger(ApiIntegration.class);
 private static final String USER_AGENT = "YourAppName/1.0";
 
 private final RestTemplate restTemplate;
 private final Gson gson;
 
 /**
  * Initializes the ApiIntegration service with a RestTemplate and Gson parser.
  */
 public ApiIntegration() {
  this.restTemplate = new RestTemplate();
  this.gson = new Gson();
 }
 
 /**
  * Represents distance and duration for a route.
  */
 public static class DistanceTime {
  
  /**
   * Distance in meters
   */
  public double distanceMeters;
  
  /**
   * Duration in seconds
   */
  public int durationSeconds;
  
  /**
   * Constructs a DistanceTime object.
   *
   * @param distanceMeters
   * @param durationSeconds
   */
  public DistanceTime(double distanceMeters, int durationSeconds) {
   this.distanceMeters = distanceMeters;
   this.durationSeconds = durationSeconds;
  }
  
  @Override
  public String toString() {
   return String.format("Distance: %.2f meters, Duration: %d seconds", distanceMeters,
    durationSeconds);
  }
 }
 
 /**
  * Geocodes a given address into latitude and longitude coordinates using Nominatim.
  *
  * @param address Address to geocode (must be non-null and non-empty)
  * @return Array of doubles: [latitude, longitude]
  * @throws ApiException if the address cannot be geocoded or if the API request fails
  */
 public double[] geocodeAddress(String address) throws ApiException {
  if (address == null || address.trim().isEmpty()) {
   throw new ApiException("Address cannot be null or empty");
  }
  
  try {
   String encodedAddress = URLEncoder.encode(address.trim(), StandardCharsets.UTF_8);
   String url = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress
    + "&format=json&limit=1";
   
   org.springframework.http.HttpEntity<String> entity =
    new org.springframework.http.HttpEntity<>(createHeaders());
   ResponseEntity<String> responseEntity =
    restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class);
   
   String response = responseEntity.getBody();
   
   if (response == null || response.isEmpty() || response.equals("[]")) {
    throw new ApiException("No results found for address: " + address);
   }
   
   JsonArray results = gson.fromJson(response, JsonArray.class);
   if (results.size() == 0) {
    throw new ApiException("No results found for address: " + address);
   }
   
   JsonObject location = results.get(0).getAsJsonObject();
   double lat = location.get("lat").getAsDouble();
   double lon = location.get("lon").getAsDouble();
   
   return new double[] {lat, lon};
   
  } catch (JsonSyntaxException e) {
   throw new ApiException("Invalid response from geocoding service", e);
  } catch (HttpClientErrorException | HttpServerErrorException e) {
   throw new ApiException("Geocoding service error: " + e.getStatusCode(), e);
  } catch (Exception e) {
   throw new ApiException("Failed to geocode address: " + address, e);
  }
 }
 
 /**
  * Calculates walking distance and duration between two coordinates using OSRM.
  *
  * @param originLat Origin latitude (valid range: -90 to 90)
  * @param originLng Origin longitude (valid range: -180 to 180)
  * @param destLat   Destination latitude (valid range: -90 to 90)
  * @param destLng   Destination longitude (valid range: -180 to 180)
  * @return DistanceTime object containing distance in meters and duration in seconds
  * @throws ApiException if the routing service fails or no route is found
  */
 public DistanceTime getDistanceTime(double originLat, double originLng, double destLat,
                                     double destLng)
  throws ApiException {
  
  validateCoordinates(originLat, originLng, "origin");
  validateCoordinates(destLat, destLng, "destination");
  
  try {
   String url = String.format(
    "https://router.project-osrm.org/route/v1/foot/%f,%f;%f,%f?overview=false",
    originLng, originLat, destLng, destLat
   );
   
   String response = restTemplate.getForObject(url, String.class);
   
   if (response == null || response.isEmpty()) {
    throw new ApiException("No response from routing service");
   }
   
   JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
   
   String code = jsonObject.has("code") ? jsonObject.get("code").getAsString() : null;
   if (!"Ok".equals(code)) {
    String message =
     jsonObject.has("message") ? jsonObject.get("message").getAsString() : "Unknown error";
    throw new ApiException("Routing error: " + message);
   }
   
   JsonArray routes = jsonObject.getAsJsonArray("routes");
   if (routes == null || routes.size() == 0) {
    throw new ApiException("No walking route found between these coordinates");
   }
   
   JsonObject route = routes.get(0).getAsJsonObject();
   double distance = route.get("distance").getAsDouble();
   int duration = (int) route.get("duration").getAsDouble();
   
   return new DistanceTime(distance, duration);
   
  } catch (JsonSyntaxException e) {
   throw new ApiException("Invalid response from routing service", e);
  } catch (HttpClientErrorException | HttpServerErrorException e) {
   throw new ApiException("Routing service error: " + e.getStatusCode(), e);
  } catch (ApiException e) {
   throw e;
  } catch (Exception e) {
   throw new ApiException("Failed to calculate route", e);
  }
 }
 
 /**
  * Calculates walking distance and duration between two addresses.
  * Geocodes the addresses first before routing.
  *
  * @param originAddress Origin address (non-null, non-empty)
  * @param destAddress   Destination address (non-null, non-empty)
  * @return DistanceTime object containing distance in meters and duration in seconds
  * @throws ApiException if geocoding or routing fails
  */
 public DistanceTime getDistanceTimeByAddress(String originAddress, String destAddress)
  throws ApiException {
  try {
   double[] originCoords = geocodeAddress(originAddress);
   Thread.sleep(1000);
   double[] destCoords = geocodeAddress(destAddress);
   return getDistanceTime(originCoords[0], originCoords[1], destCoords[0], destCoords[1]);
  } catch (InterruptedException e) {
   Thread.currentThread().interrupt();
   throw new ApiException("Operation interrupted", e);
  }
 }
 
 private void validateCoordinates(double lat, double lng, String label) throws ApiException {
  if (lat < -90 || lat > 90) {
   throw new ApiException(
    String.format("Invalid %s latitude: %f (must be between -90 and 90)", label, lat));
  }
  if (lng < -180 || lng > 180) {
   throw new ApiException(
    String.format("Invalid %s longitude: %f (must be between -180 and 180)", label, lng));
  }
 }
 
 private HttpHeaders createHeaders() {
  HttpHeaders headers = new HttpHeaders();
  headers.set("User-Agent", USER_AGENT);
  return headers;
 }
 
 /**
  * Custom exception for API-related errors.
  */
 public static class ApiException extends Exception {
  public ApiException(String message) {
   super(message);
  }
  
  public ApiException(String message, Throwable cause) {
   super(message, cause);
  }
 }
}
