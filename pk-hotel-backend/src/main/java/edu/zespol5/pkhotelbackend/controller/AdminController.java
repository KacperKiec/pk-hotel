package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.connectors.RoomConvenienceRequestDTO;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.hotel.HotelDTO;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomDTO;
import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final RoomService roomService;
    private final HotelService hotelService;
    private final ConvenienceService convenienceService;
    private final ExtraService extraService;
    private final UserService userService;

    public AdminController(RoomService roomService, HotelService hotelService, ConvenienceService convenienceService, ExtraService extraService, UserService userService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.convenienceService = convenienceService;
        this.extraService = extraService;
        this.userService = userService;
    }

    @PostMapping(value = "/hotel")
    public ResponseEntity<HotelDTO> addHotel(@RequestBody Hotel hotel) {
        var result = hotelService.save(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(value = "hotel")
    public ResponseEntity<?> deleteHotel(@RequestBody Hotel hotel) {
        hotelService.deleteHotel(hotel);
        return ResponseEntity.noContent().build();
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

    @DeleteMapping
    public ResponseEntity<?> removeRoomConveniences(@RequestBody RoomConvenienceRequestDTO param) {
        roomService.removeConvenience(param.getRoom(), param.getConveniencesIds().getFirst());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/convenience")
    public ResponseEntity<Convenience> addConvenience(@RequestBody Convenience convenience) {
        var result = convenienceService.save(convenience);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(value = "/convenience")
    public ResponseEntity<?> deleteConvenience(@RequestBody Convenience convenience) {
        convenienceService.deleteConvenience(convenience);
        return ResponseEntity.noContent().build();
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

    @DeleteMapping(value = "/extra")
    public ResponseEntity<?> deleteExtra(@RequestBody Extra extra) {
        extraService.deleteExtra(extra);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/extra")
    public ResponseEntity<Page<Extra>> getAllExtras(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = extraService.getAllExtras(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = userService.getAllUsers(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<Page<UserDTO>> getAllClients(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = userService.getAllClients(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/admins")
    public ResponseEntity<Page<UserDTO>> getAllAdmins(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = userService.getAllAdmins(pageable);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        var result = userService.updateUser(user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<?> deleteUser(@RequestBody User user, Authentication auth) {
        var currentUserEmail = auth.getName();
        userService.deleteUser(user, currentUserEmail);
        return ResponseEntity.noContent().build();
    }

}
