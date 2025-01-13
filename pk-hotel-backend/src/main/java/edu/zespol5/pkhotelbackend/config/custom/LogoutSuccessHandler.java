package edu.zespol5.pkhotelbackend.config.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class handles successful logout events in the application.
 * It implements the {@link org.springframework.security.web.authentication.logout.LogoutSuccessHandler}
 * interface to define custom behavior after a user logs out.
 *
 * <p>Upon successful logout, the handler returns a JSON response indicating
 * that the logout was successful.</p>
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    /**
     * Constructs a new {@code LogoutSuccessHandler} with the specified {@link ObjectMapper}.
     *
     * @param objectMapper the object mapper used to serialize objects to JSON
     */
    public LogoutSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handles actions to be taken upon successful logout.
     * This method sets the response status to 204 (No Content) and sends a
     * JSON response indicating the successful logout.
     *
     * @param request the {@link HttpServletRequest} that resulted in a logout
     * @param response the {@link HttpServletResponse} used to send the success response
     * @param authentication the {@link Authentication} object containing the user's details
     *                        (can be {@code null} if the user was not authenticated)
     * @throws IOException if an input or output error occurs during the writing of the response
     * @throws ServletException if an error occurs during request processing
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Logout successful\"}");
    }
}
