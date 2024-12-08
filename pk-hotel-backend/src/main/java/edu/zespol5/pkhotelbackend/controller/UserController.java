package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.reservation.ReservationDTO;
import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.service.ReservationService;
import edu.zespol5.pkhotelbackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserProfile(Authentication auth) {
        String email = auth.getName();
        var user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<Page<ReservationDTO>> getReservations(Authentication auth, @RequestParam(defaultValue = "0") int page) {
        String email = auth.getName();
        Pageable pageable = PageRequest.of(page, 10);
        var result = reservationService.getReservationsBy(null, null, email, pageable);
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(Authentication auth, @RequestBody User user) {
        var result = userService.updateUser(user);
        return ResponseEntity.ok(result);
    }
}
