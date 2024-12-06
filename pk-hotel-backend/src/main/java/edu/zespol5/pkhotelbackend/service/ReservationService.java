package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.*;
import edu.zespol5.pkhotelbackend.model.connectors.ReservationExtra;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationDTO;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomSpecification;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationStatus;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ExtraRepository extraRepository;

    public ReservationService(ReservationRepository repository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository, ExtraRepository extraRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.extraRepository = extraRepository;
    }

    @Transactional
    public ReservationDTO save(Reservation reservation, List<Integer> extraIds, String email) {

        var existingUser = userRepository.findUserByEmail(reservation.getUser().getEmail()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        if(!existingUser.getEmail().equals(email)) {
            throw new IllegalOperationException("User incompatibility");
        }

        reservation.setUser(existingUser);

        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(
                reservation.getRoom().getHotel().getId(), reservation.getRoom().getRoomNr()
        ).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        reservation.setRoom(existingRoom);

        if(reservation.getCheckInDate().isAfter(reservation.getCheckOutDate())) {
            throw new IllegalOperationException("Check In date can not be after Check Out date!");
        }

        Specification<Room> spec = Specification.where(RoomSpecification.hasHotelId(existingRoom.getHotel().getId()))
                .and(RoomSpecification.hasRoomNr(existingRoom.getRoomNr()))
                .and(RoomSpecification.isAvailable(reservation.getCheckInDate(), reservation.getCheckOutDate()));
        if(roomRepository.findAll(spec).isEmpty()) {
            throw new IllegalOperationException("Given dates are not available for this room");
        }

        for(Integer extraId : extraIds) {
            var ext = extraRepository.findExtraById(extraId).orElseThrow(
                    () -> new ExtraNotFoundException("Extra not found")
            );

            ReservationExtra reservationExtra = new ReservationExtra(reservation, ext);
            reservation.addExtra(reservationExtra);
        }

        reservation.setStatus(ReservationStatus.ACCEPTED);

        return toDTO(repository.save(reservation));
    }

    public ReservationDTO updateReservation(Reservation reservation) {
        var existingReservation = repository.findReservationById(reservation.getId()).orElseThrow(
                () -> new ReservationNotFoundException("Reservation not found")
        );

        if (reservation.getUser() != null) {
            var existingUser = userRepository.findUserByEmail(reservation.getUser().getEmail()).orElseThrow(
                    () -> new UserNotFoundException("User not found")
            );
            existingReservation.setUser(existingUser);
        }

        if (reservation.getRoom() != null) {
            var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(reservation.getRoom().getHotel().getId(), reservation.getRoom().getRoomNr()).orElseThrow(
                    () -> new RoomNotFoundException("Room not found")
            );
            existingReservation.setRoom(existingRoom);
        }

        if (reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null &&
                reservation.getCheckInDate().isBefore(reservation.getCheckOutDate())) {
            existingReservation.setCheckInDate(reservation.getCheckInDate());
            existingReservation.setCheckOutDate(reservation.getCheckOutDate());
        } else throw new IllegalOperationException("Check In date can not be after Check Out date!");

        if (reservation.getStatus() != null) {
            existingReservation.setStatus(reservation.getStatus());
        }

        if (reservation.getExtras() != null && !reservation.getExtras().isEmpty()) {
            existingReservation.getExtras().clear();
            existingReservation.getExtras().addAll(reservation.getExtras());
        }

        return toDTO(repository.save(existingReservation));
    }


    public Reservation getReservationById(int id) {
        return repository.findReservationById(id).orElseThrow(
                () -> new ReservationNotFoundException("Reservation with id " + id + " was not found")
        );
    }

    public Page<ReservationDTO> getAllReservations(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
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
        dto.setId(reservation.getId());
        dto.setClientName(reservation.getUser().getFirstName());
        dto.setClientLastName(reservation.getUser().getLastName());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        dto.setStatus(reservation.getStatus());
        dto.setHotelName(reservation.getRoom().getHotel().getName());
        dto.setRoomNr(reservation.getRoom().getRoomNr());

        int days = (int) ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
        double price = 0;

        price += days * roomRepository.findRoomByHotel_IdAndRoomNr(
                reservation.getRoom().getHotel().getId(), reservation.getRoom().getRoomNr()
        ).orElseThrow().getPrice();

        for(ReservationExtra extra: reservation.getExtras()) {
            price += extra.getExtra().getPricePerDay();
        }
        dto.setTotalPrice(price);

        return dto;
    }
}
