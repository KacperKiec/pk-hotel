package edu.zespol5.pkhotelbackend.repository.convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConvenienceRepository {
    Convenience save(Convenience convenience);
    Optional<Convenience> findConvenienceById(int id);
    Page<Convenience> findAll(Pageable pageable);
    List<Convenience> findConveniencesByName(String name);

    @Query("SELECT rc.convenience FROM RoomConvenience rc WHERE rc.room.roomNr = :roomNr AND rc.room.hotel.id = :hotelId")
    List<Convenience> findConvenienceByRoomId(@Param("roomNr") int roomNr, @Param("hotelId") int hotelId);
}
