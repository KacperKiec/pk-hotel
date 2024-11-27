package edu.zespol5.pkhotelbackend.repository.convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface JpaConvenienceRepository extends JpaRepository<Convenience, Integer>, ConvenienceRepository {

    @Query("SELECT rc.convenience FROM RoomConvenience rc WHERE rc.room.roomNr = :roomNr AND rc.room.hotel.id = :hotelId")
    List<Convenience> findConvenienceByRoomId(@Param("roomNr") int roomNr, @Param("hotelId") int hotelId);
}
