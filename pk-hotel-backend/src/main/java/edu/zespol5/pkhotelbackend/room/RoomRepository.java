package edu.zespol5.pkhotelbackend.room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    List<Room> findAll();
    Optional<Room> findRoomByHotel_IdAndRoomNr(int hotelId, int roomNr);
}
