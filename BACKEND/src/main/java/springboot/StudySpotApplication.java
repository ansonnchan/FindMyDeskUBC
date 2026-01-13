package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import controller.MatchingScoreController;
import controller.StudySpotsController;

/**
 * Main Spring Boot application class for the Study Spot application.
 * Configures component scanning, starts the Spring context, and provides
 * beans for controllers that require manual wiring.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"springboot", "controller", "api", "config"})
public class StudySpotApplication {

    /**
     * Entry point for the Spring Boot application.
     * 
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(StudySpotApplication.class, args);
    }

    /**
     * Provides a bean for MatchingScoreController, initializing it with all study spots
     * from the StudySpotsController. This allows it to be injected where needed.
     *
     * @param spotsController the StudySpotsController providing study spot data
     * @return a new instance of MatchingScoreController
     */
    @Bean
    public MatchingScoreController matchingScoreController(StudySpotsController spotsController) {
        return new MatchingScoreController(spotsController.getAllStudySpots());
    }
}
