package edu.zespol5.pkhotelbackend.reservation;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findReservationById(int id);
    List<Reservation> findAll();
    List<Reservation> findAll(Specification<Reservation> spec);
}
