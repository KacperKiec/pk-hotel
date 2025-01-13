package edu.zespol5.pkhotelbackend.model.reservation_extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an association between a reservation and an extra service or item added to that reservation.
 * <p>
 * This entity manages the relationship between a specific reservation and the extras (such as additional services
 * or products) associated with it. The association is stored in the {@code reservation_extra} table.
 * </p>
 */
@Entity
@Table(name = "reservation_extra")
@Getter
@Setter
public class ReservationExtra {

    /**
     * The composite primary key that uniquely identifies the association between a reservation and an extra.
     */
    @EmbeddedId
    private ReservationExtraId id;

    /**
     * The reservation associated with this extra.
     */
    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    /**
     * The extra service or item associated with this reservation.
     */
    @ManyToOne
    @MapsId("extraId")
    @JoinColumn(name = "extra_id")
    private Extra extra;

    /**
     * Default constructor.
     */
    public ReservationExtra() {}

    /**
     * Constructs a new {@link ReservationExtra} with the given reservation and extra.
     * <p>
     * The constructor sets up the composite ID based on the reservation and extra provided.
     * </p>
     *
     * @param reservation the reservation associated with this extra
     * @param extra the extra service or item associated with this reservation
     */
    public ReservationExtra(Reservation reservation, Extra extra) {
        setReservation(reservation);
        setExtra(extra);
        ReservationExtraId id = new ReservationExtraId();
        id.setReservationId(reservation.getId());
        id.setExtraId(extra.getId());
        setId(id);
    }
}
