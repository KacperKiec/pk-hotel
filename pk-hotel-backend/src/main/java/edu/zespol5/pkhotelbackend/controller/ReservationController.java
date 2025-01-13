package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.reservation_extra.ReservationExtraRequestDTO;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationDTO;
import edu.zespol5.pkhotelbackend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for handling reservation-related requests, including creating reservations.
 * <p>
 * This controller provides an endpoint for making new reservations. Users can specify reservation details
 * and extra services, and the controller will create the reservation and associate it with the authenticated user.
 * </p>
 */
@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * Constructor for initializing the controller with the given reservation service.
     *
     * @param reservationService the service responsible for reservation operations
     */
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(Authentication auth, @RequestBody ReservationExtraRequestDTO param) {
        var result = reservationService.save(param.getReservation(), param.getExtraIds(), auth.getName());
        return ResponseEntity.ok(result);
    }
}
