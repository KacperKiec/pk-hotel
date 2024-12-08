package edu.zespol5.pkhotelbackend.repository.image;

import edu.zespol5.pkhotelbackend.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    Image save(Image image);
    Optional<Image> findImageById(int id);
    List<Image> findAll();
    void deleteById(int id);

    @Query("SELECT ri.image FROM RoomImage ri WHERE ri.room.roomNr = :roomNr AND ri.room.hotel.id = :hotelId")
    List<Image> findImageByRoomId(@Param("roomNr") int roomNr, @Param("hotelId") int hotelId);
}
