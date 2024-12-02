package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.connectors.RoomConvenienceRequestDTO;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.hotel.HotelDTO;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomDTO;
import edu.zespol5.pkhotelbackend.service.ConvenienceService;
import edu.zespol5.pkhotelbackend.service.ExtraService;
import edu.zespol5.pkhotelbackend.service.HotelService;
import edu.zespol5.pkhotelbackend.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final RoomService roomService;
    private final HotelService hotelService;
    private final ConvenienceService convenienceService;
    private final ExtraService extraService;

    public AdminController(RoomService roomService, HotelService hotelService, ConvenienceService convenienceService, ExtraService extraService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.convenienceService = convenienceService;
        this.extraService = extraService;
    }

    @PostMapping(value = "/hotel")
    public ResponseEntity<HotelDTO> addHotel(@RequestBody Hotel hotel) {
        var result = hotelService.save(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping(value = "/room")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody Room room) {
        var result = roomService.saveRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PatchMapping(value = "/room-conveniences")
    public ResponseEntity<RoomDTO> addRoomConveniences(@RequestBody RoomConvenienceRequestDTO param) {
        var result = roomService.addConveniences(param.getRoom(), param.getConveniencesIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping(value = "/convenience")
    public ResponseEntity<Convenience> addConvenience(@RequestBody Convenience convenience) {
        var result = convenienceService.save(convenience);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(value = "/convenience")
    public ResponseEntity<Page<Convenience>> getAllConveniences(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = convenienceService.getAllConveniences(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/extra")
    public ResponseEntity<Extra> addExtra(@RequestBody Extra extra) {
        var result = extraService.save(extra);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(value = "/extra")
    public ResponseEntity<Page<Extra>> getAllExtras(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = extraService.getAllExtras(pageable);
        return ResponseEntity.ok(result);
    }
}
