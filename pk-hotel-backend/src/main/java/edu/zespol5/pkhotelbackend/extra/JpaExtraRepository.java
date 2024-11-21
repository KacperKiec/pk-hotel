package edu.zespol5.pkhotelbackend.extra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface JpaExtraRepository extends JpaRepository<Extra, Integer>, ExtraRepository {

    @Query("SELECT re.extra FROM ReservationExtra re WHERE re.reservation.id = :id")
    List<Extra> findExtrasByReservationId(@Param("id") int id);
}
