package edu.zespol5.pkhotelbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the {@link ObjectMapper} bean.
 * <p>
 * This class provides a Spring-managed {@code ObjectMapper} instance configured to handle
 * Java 8 date and time types (e.g., {@link java.time.LocalDate}) and to disable the
 * serialization of dates as timestamps.
 * </p>
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Creates and configures an {@link ObjectMapper} bean.
     * <p>
     * The configured {@code ObjectMapper} is set to:
     * <ul>
     *     <li>Register the {@link JavaTimeModule} for handling Java 8 date and time types.</li>
     *     <li>Disable the serialization of dates as timestamps.</li>
     * </ul>
     * </p>
     *
     * @return a configured {@link ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
