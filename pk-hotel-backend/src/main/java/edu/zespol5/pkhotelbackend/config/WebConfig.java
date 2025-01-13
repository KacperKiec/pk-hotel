package edu.zespol5.pkhotelbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

/**
 * Configuration class for web-related settings in the application.
 * <p>
 * This class enables support for Spring Data web functionality and configures
 * Cross-Origin Resource Sharing (CORS) settings.
 * </p>
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class WebConfig {

    /**
     * Creates a {@link CorsFilter} bean to handle CORS requests.
     * <p>
     * This configuration allows requests from the specified frontend origin and
     * supports common HTTP methods and headers, including authentication headers.
     * </p>
     *
     * <ul>
     *     <li><b>Allowed Origins:</b> http://localhost:3000 (frontend application).</li>
     *     <li><b>Allowed Methods:</b> GET, POST, PUT, DELETE, OPTIONS, PATCH.</li>
     *     <li><b>Allowed Headers:</b> Authorization, Content-Type.</li>
     *     <li><b>Exposed Headers:</b> Authorization.</li>
     *     <li><b>Credentials:</b> Cookies and other credentials are allowed.</li>
     * </ul>
     *
     * @return a configured {@link CorsFilter} instance
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Allow frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); // Enable cookies if needed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
