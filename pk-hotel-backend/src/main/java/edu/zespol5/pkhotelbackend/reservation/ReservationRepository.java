package edu.zespol5.pkhotelbackend.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findReservationById(int id);
    List<Reservation> findAll();
    List<Reservation> findReservationsByClient_Id(int client_id);
}
