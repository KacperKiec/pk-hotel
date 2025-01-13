package edu.zespol5.pkhotelbackend.config.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class handles successful login events in the application.
 * It implements the {@link AuthenticationSuccessHandler} interface
 * to define custom behavior after successful authentication.
 *
 * <p>Upon successful login, the handler retrieves the authenticated user's
 * details and returns a JSON representation of the user's information.</p>
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new {@code LoginSuccessHandler} with the specified
     * {@link UserService} and {@link ObjectMapper}.
     *
     * @param userService the service used to retrieve user details
     * @param objectMapper the object mapper used to serialize objects to JSON
     */
    public LoginSuccessHandler(@Lazy UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    /**
     * Handles actions to be taken upon successful authentication.
     * This method retrieves the authenticated user's details and returns
     * a JSON response containing the user's information.
     *
     * @param request the {@link HttpServletRequest} that resulted in successful authentication
     * @param response the {@link HttpServletResponse} used to send the success response
     * @param authentication the {@link Authentication} object containing the authenticated user's details
     * @throws IOException if an input or output error occurs during the writing of the response
     * @throws ServletException if an error occurs during request processing
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserDTO userDTO = userService.getUserByEmail(userDetails.getUsername());

        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(userDTO));
    }
}
