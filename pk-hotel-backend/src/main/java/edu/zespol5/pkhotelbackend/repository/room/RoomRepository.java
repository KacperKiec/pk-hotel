package edu.zespol5.pkhotelbackend.repository.room;

import edu.zespol5.pkhotelbackend.model.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findRoomByHotel_IdAndRoomNr(int hotelId, int roomNr);
    Page<Room> findAll(Pageable pageable);
    Page<Room> findAll(Specification<Room> spec, Pageable pageable);
}
