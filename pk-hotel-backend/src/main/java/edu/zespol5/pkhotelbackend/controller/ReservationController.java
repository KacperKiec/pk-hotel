package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.reservation_extra.ReservationExtraRequestDTO;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationDTO;
import edu.zespol5.pkhotelbackend.service.ExtraService;
import edu.zespol5.pkhotelbackend.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private final ExtraService extraService;

    /**
     * Constructor for initializing the controller with the given reservation service.
     *
     * @param reservationService the service responsible for reservation operations
     * @param extraService the service responsible for extra operations
     */
    public ReservationController(ReservationService reservationService, ExtraService extraService) {
        this.reservationService = reservationService;
        this.extraService = extraService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(Authentication auth, @RequestBody ReservationExtraRequestDTO param) {
        var result = reservationService.save(param.getReservation(), param.getExtraIds(), auth.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/extra")
    public ResponseEntity<Page<Extra>> getAllExtras(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = extraService.getAllExtras(pageable);
        return ResponseEntity.ok(result);
    }
}
