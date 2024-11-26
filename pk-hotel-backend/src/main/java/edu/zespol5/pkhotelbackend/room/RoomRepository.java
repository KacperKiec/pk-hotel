package edu.zespol5.pkhotelbackend.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findRoomByHotel_IdAndRoomNr(int hotelId, int roomNr);
    Page<Room> findAll(Pageable pageable);
    List<Room> findAll(Specification<Room> spec);
}
