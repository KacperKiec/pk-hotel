package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller responsible for handling user authentication-related operations, such as registration.
 * <p>
 * This controller provides endpoints for registering new users in the system.
 * </p>
 */
@Controller
public class AuthController {

    private final UserService userService;

    /**
     * Constructs an instance of {@code AuthController} with the given {@link UserService}.
     *
     * @param userService the service managing user-related operations
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user in the system.
     * <p>
     * The provided {@link User} object is used to create a new account.
     * A successful registration returns the details of the created user.
     * </p>
     *
     * @param user the {@link User} object containing the new user's details
     * @return a {@link ResponseEntity} containing the created {@link UserDTO} and the HTTP status
     * @throws org.springframework.dao.DataIntegrityViolationException if there are issues with database constraints
     */
    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        var newUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
