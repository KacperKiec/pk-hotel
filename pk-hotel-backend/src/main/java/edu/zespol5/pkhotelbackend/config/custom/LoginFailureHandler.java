package edu.zespol5.pkhotelbackend.config.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A custom implementation of the {@link AuthenticationFailureHandler} interface that handles
 * failed authentication attempts.
 *
 * <p>
 * This class is annotated with {@link Component}, making it a Spring-managed bean. It is automatically
 * discovered and registered in the Spring application context.
 * </p>
 *
 * <p>
 * On an authentication failure, this handler responds with a JSON payload containing an error
 * message and status code.
 * </p>
 *
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    /**
     * Constructor for {@code LoginFailureHandler}.
     *
     * @param objectMapper the {@link ObjectMapper} used for converting Java objects to JSON.
     */
    public LoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handles authentication failure events.
     *
     * <p>
     * When an authentication failure occurs, this method generates a JSON response with an
     * appropriate error message and status code.
     * </p>
     *
     * @param request   the {@link HttpServletRequest} in which the authentication failure occurred.
     * @param response  the {@link HttpServletResponse} to which the failure response is sent.
     * @param exception the {@link AuthenticationException} containing details about the failure.
     * @throws IOException      if an input or output error occurs while handling the failure.
     * @throws ServletException if the failure handling cannot proceed.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Map<String, Object> responseData = new HashMap<>();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        responseData.put("message", "Authentication failed: " + exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        objectMapper.writeValue(response.getWriter(), responseData);
    }
}
