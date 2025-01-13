package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.*;
import edu.zespol5.pkhotelbackend.model.reservation_extra.ReservationExtra;
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

/**
 * {@code ReservationService} provides business logic for managing hotel reservations.
 * This service includes methods for creating, updating, retrieving, and deleting reservations,
 * as well as calculating prices and validating reservation constraints.
 *
 * <p>
 * The service interacts with multiple repositories to manage reservations, rooms, users, and extras.
 * It ensures that rooms are available for the requested dates, validates reservation status, and handles
 * the addition of extras to a reservation.
 * </p>
 *
 * @see Reservation
 * @see ReservationRepository
 * @see UserRepository
 * @see RoomRepository
 * @see ExtraRepository
 * @see HotelRepository
 * @see ReservationDTO
 */
@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ExtraRepository extraRepository;

    /**
     * Constructs a new {@code ReservationService} with the provided repositories.
     *
     * @param repository the {@link ReservationRepository} used for accessing reservation data.
     * @param userRepository the {@link UserRepository} used for accessing user data.
     * @param hotelRepository the {@link HotelRepository} used for accessing hotel data.
     * @param roomRepository the {@link RoomRepository} used for accessing room data.
     * @param extraRepository the {@link ExtraRepository} used for accessing extra services data.
     */
    public ReservationService(ReservationRepository repository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository, ExtraRepository extraRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.extraRepository = extraRepository;
    }

    /**
     * Saves a new reservation with the provided details, including extras and user email.
     *
     * @param reservation the reservation to be saved.
     * @param extraIds a list of extra IDs to be added to the reservation.
     * @param email the email of the user making the reservation.
     * @return the saved {@link ReservationDTO} object containing reservation details.
     * @throws UserNotFoundException if the user is not found.
     * @throws RoomNotFoundException if the room is not found.
     * @throws IllegalOperationException if the check-in date is after the check-out date or the room is unavailable.
     * @throws ExtraNotFoundException if an extra is not found.
     */
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

    /**
     * Updates an existing reservation with the provided details.
     *
     * @param reservation the updated reservation details.
     * @return the updated {@link ReservationDTO} object containing reservation details.
     * @throws ReservationNotFoundException if the reservation is not found.
     * @throws IllegalOperationException if the check-in date is after the check-out date.
     */
    @Transactional
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

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the ID of the reservation.
     * @return the {@link Reservation} object for the given ID.
     * @throws ReservationNotFoundException if the reservation is not found.
     */
    public Reservation getReservationById(int id) {
        return repository.findReservationById(id).orElseThrow(
                () -> new ReservationNotFoundException("Reservation with id " + id + " was not found")
        );
    }

    /**
     * Retrieves all reservations with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link ReservationDTO} objects.
     */
    public Page<ReservationDTO> getAllReservations(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Retrieves reservations by specific filters such as status, hotel ID, and user email.
     *
     * @param status the reservation status filter.
     * @param hotelId the hotel ID filter.
     * @param email the user email filter.
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link ReservationDTO} objects matching the filters.
     * @throws HotelNotFoundException if the hotel with the given ID is not found.
     * @throws UserNotFoundException if the user with the given email is not found.
     */
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

    /**
     * Converts a {@link Reservation} object to a {@link ReservationDTO} object.
     *
     * @param reservation the reservation to be converted.
     * @return the converted {@link ReservationDTO} object.
     */
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
