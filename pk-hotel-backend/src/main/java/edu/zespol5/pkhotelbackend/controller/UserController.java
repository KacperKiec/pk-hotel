package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.UserDTO;
import edu.zespol5.pkhotelbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserProfile(Authentication auth) {
        String email = auth.getName();
        var user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
