package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.Reservation;
import edu.zespol5.pkhotelbackend.model.ReservationStatus;
import edu.zespol5.pkhotelbackend.exception.ReservationNotFoundException;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    public ReservationService(ReservationRepository repository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation getReservationById(int id) {
        return repository.findReservationById(id).orElseThrow(
                () -> new ReservationNotFoundException("Reservation with id " + id + " was not found")
        );
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public List<Reservation> getReservationsBy(
            ReservationStatus status,
            int hotelId,
            int clientId) {

        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );

        userRepository.findClientById(clientId).orElseThrow(
                () -> new UserNotFoundException("Client with id " + clientId + " was not found")
        );

        Specification<Reservation> spec = Specification.where(ReservationSpecification.hasStatus(status)
                .and(ReservationSpecification.hasHotelId(hotelId)))
                .and(ReservationSpecification.hasClientId(clientId));
        return repository.findAll(spec);
    }
}
