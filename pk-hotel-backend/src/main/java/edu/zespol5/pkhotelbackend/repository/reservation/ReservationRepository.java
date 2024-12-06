package edu.zespol5.pkhotelbackend.repository.reservation;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findReservationById(int id);
    List<Reservation> findAll();
    Page<Reservation> findAll(Pageable pageable);
    Page<Reservation> findAll(Specification<Reservation> spec, Pageable pageable);
    void deleteById(int id);
}
