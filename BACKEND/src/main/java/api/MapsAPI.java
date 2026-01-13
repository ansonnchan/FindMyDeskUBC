package api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for exposing Google Maps API key to the frontend.
 * Retrieves the key from application properties or environment variables.
 */
@RestController
public class MapsAPI {
 
 @Value("${google.maps.api.key}")
 private String googleMapsApiKey;
 
 /**
  * Retrieves the Google Maps API key as a JSON object.
  *
  * @return Map containing the API key with key "apiKey"
  */
 @GetMapping("/api/google-maps-api-key")
 public Map<String, String> getApiKey() {
  return Map.of("apiKey", googleMapsApiKey);
 }
}
