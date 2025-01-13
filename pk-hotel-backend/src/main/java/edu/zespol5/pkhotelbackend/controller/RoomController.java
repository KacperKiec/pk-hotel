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
import java.util.List;

/**
 * Controller responsible for handling room-related requests, such as retrieving room details,
 * searching rooms based on filters, and listing all available rooms.
 * <p>
 * This controller provides various endpoints for retrieving room information. Users can fetch
 * all rooms, search for rooms with specific filters (such as price, standard, availability, etc.),
 * or view details of a specific room in a hotel.
 * </p>
 */
@Controller
@RequestMapping(value = "/rooms")
public class RoomController {
    private final RoomService roomService;

    /**
     * Constructor for initializing the controller with the given room service.
     *
     * @param roomService the service responsible for room operations
     */
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
            @RequestParam(required = false) Integer hotelId,
            @RequestParam(required = false) Integer roomNr,
            @RequestParam(required = false) Double lowerPriceLimit,
            @RequestParam(required = false) Double upperPriceLimit,
            @RequestParam(required = false) RoomStandard standard,
            @RequestParam(required = false) Integer places,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) List<String> countries,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        var result = roomService.getRoomsBy(
                hotelId, roomNr, lowerPriceLimit, upperPriceLimit, standard, places, startDate, endDate, cities, countries, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{hotelId}-{roomNr}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Integer hotelId, @PathVariable Integer roomNr) {
        var result = roomService.getRoomById(hotelId, roomNr);
        return ResponseEntity.ok(result);
    }
}
