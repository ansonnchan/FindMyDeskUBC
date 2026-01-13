package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Configuration class for customizing Jackson's ObjectMapper.
 * Registers JavaTimeModule to handle Java 8+ date and time types.
 */
@Configuration
public class JacksonConfig {
 
 /**
  * Creates and configures an ObjectMapper bean with JavaTimeModule.
  *
  * @return Configured ObjectMapper instance
  */
 @Bean
 public ObjectMapper objectMapper() {
  return Jackson2ObjectMapperBuilder.json()
   .modules(new JavaTimeModule())
   .build();
 }
}
