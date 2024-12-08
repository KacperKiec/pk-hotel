package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.room.RoomDTO;
import edu.zespol5.pkhotelbackend.model.room.RoomStandard;
import edu.zespol5.pkhotelbackend.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Page<RoomDTO>> getAllRooms(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = roomService.getAllRooms(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<RoomDTO>> getRoomsWithFilters(
            Integer hotelId,
            Integer roomNr,
            Double lowerPriceLimit,
            Double upperPriceLimit,
            RoomStandard standard,
            Integer places,
            LocalDate startDate,
            LocalDate endDate,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        var result = roomService.getRoomsBy(
                hotelId, roomNr, lowerPriceLimit, upperPriceLimit, standard, places, startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{hotelId}-{roomNr}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Integer hotelId, @PathVariable Integer roomNr) {
        var result = roomService.getRoomById(hotelId, roomNr);
        return ResponseEntity.ok(result);
    }
}
