package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for CORS (Cross-Origin Resource Sharing) settings.
 * Allows the frontend at specific origins to access API endpoints.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
 /**
  * Configures CORS mappings for API endpoints.
  * Allows specific origins, HTTP methods, and headers.
  *
  * @param registry CorsRegistry used to register CORS configuration
  */
 @Override
 public void addCorsMappings(CorsRegistry registry) {
  registry.addMapping("/api/**")
   .allowedOrigins(
    "http://127.0.0.1:5500",
    "http://localhost:5500"
   )
   .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
   .allowedHeaders("*");
 }
}
