package edu.zespol5.pkhotelbackend.config.custom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class handles authentication failures in the application.
 * It implements the {@link AuthenticationEntryPoint} interface and is used
 * to return an appropriate HTTP response when a user tries to access a secured
 * resource without proper authentication.
 *
 * <p>The handler sets the response status to 401 (Unauthorized) and returns
 * a JSON response with an error code and message.</p>
 */
@Component
public class AuthenticationFailureHandler implements AuthenticationEntryPoint {

    /**
     * Handles the commencement of an authentication failure.
     * This method is triggered when an unauthenticated user attempts to access
     * a protected resource.
     *
     * @param request the {@link HttpServletRequest} that resulted in an authentication failure
     * @param response the {@link HttpServletResponse} to send the error response
     * @param authException the exception that triggered this authentication failure
     * @throws IOException if an input or output error occurs during the writing of the response
     * @throws ServletException if the handling of the request fails
     */
    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"code\": \"818\", \"message\": \"Access denied. Please log in.\"}");
    }
}
