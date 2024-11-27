package edu.zespol5.pkhotelbackend.repository.reservation;

import edu.zespol5.pkhotelbackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JpaReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation>, ReservationRepository {

}
