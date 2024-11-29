package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationDTO;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationStatus;
import edu.zespol5.pkhotelbackend.exception.ReservationNotFoundException;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ReservationDTO> getReservationsBy(
            ReservationStatus status,
            Integer hotelId,
            String email,
            Pageable pageable) {

        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );

        var user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Client with email " + email + " was not found")
        );

        Specification<Reservation> spec = Specification.where(ReservationSpecification.hasStatus(status)
                .and(ReservationSpecification.hasHotelId(hotelId)))
                .and(ReservationSpecification.hasClientId(user.getId()));
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    private ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setClientName(reservation.getUser().getFirstName());
        dto.setClientLastName(reservation.getUser().getLastName());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        dto.setStatus(reservation.getStatus());
        dto.setHotelName(reservation.getRoom().getHotel().getName());
        dto.setRoomNr(reservation.getRoom().getRoomNr());
        return dto;
    }
}
