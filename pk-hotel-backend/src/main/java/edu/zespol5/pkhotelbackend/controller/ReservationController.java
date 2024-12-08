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

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(Authentication auth, @RequestBody ReservationExtraRequestDTO param) {
        var result = reservationService.save(param.getReservation(), param.getExtraIds(), auth.getName());
        return ResponseEntity.ok(result);
    }
}
