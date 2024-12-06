package edu.zespol5.pkhotelbackend.model.connectors;

import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservation_extra")
@Getter
@Setter
public class ReservationExtra {

    @EmbeddedId
    private ReservationExtraId id;

    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @MapsId("extraId")
    @JoinColumn(name = "extra_id")
    private Extra extra;

    public ReservationExtra() {}

    public ReservationExtra(Reservation reservation, Extra extra) {
        setReservation(reservation);
        setExtra(extra);
        ReservationExtraId id = new ReservationExtraId();
        id.setReservationId(reservation.getId());
        id.setExtraId(extra.getId());
        setId(id);
    }
}
