package edu.zespol5.pkhotelbackend.model.reservation;

import edu.zespol5.pkhotelbackend.model.reservation_extra.ReservationExtra;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a reservation made by a user for a specific room in a hotel.
 * <p>
 * This class maps to the "reservation" table in the database and contains details about the reservation,
 * including the client (user), room, check-in/check-out dates, reservation status, and any additional reservation extras.
 * </p>
 */
@Data
@Entity
@Table(name = "reservation")
public class Reservation {

    /**
     * The unique identifier of the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The user who made the reservation.
     * <p>
     * This field is a many-to-one relationship with the {@link User} entity.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    /**
     * The room that has been reserved.
     * <p>
     * This field represents a many-to-one relationship with the {@link Room} entity.
     * It is a composite key linking to both the hotel and room numbers.
     * </p>
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id"),
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr")
    })
    private Room room;

    /**
     * The check-in date for the reservation.
     */
    private LocalDate checkInDate;

    /**
     * The check-out date for the reservation.
     */
    private LocalDate checkOutDate;

    /**
     * The status of the reservation.
     * <p>
     * This field is an enumeration representing various possible statuses, such as {@code CONFIRMED}, {@code PENDING}, etc.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    /**
     * The extras associated with the reservation.
     * <p>
     * This field represents a one-to-many relationship with the {@link ReservationExtra} entity.
     * The extras may include additional services or items added to the reservation.
     * </p>
     */
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ReservationExtra> extras = new HashSet<>();

    /**
     * Adds an extra to the reservation.
     * <p>
     * This method is used to associate a new extra (like breakfast, parking, etc.) with the reservation.
     * </p>
     *
     * @param extra the {@link ReservationExtra} to add.
     */
    public void addExtra(ReservationExtra extra) {
        extras.add(extra);
    }

    /**
     * Removes an extra from the reservation.
     * <p>
     * This method is used to disassociate an extra from the reservation.
     * </p>
     *
     * @param extra the {@link ReservationExtra} to remove.
     */
    public void removeExtra(ReservationExtra extra) {
        extras.remove(extra);
    }
}
