package edu.zespol5.pkhotelbackend.reservation;

import edu.zespol5.pkhotelbackend.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface JpaReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation>, ReservationRepository {

}
