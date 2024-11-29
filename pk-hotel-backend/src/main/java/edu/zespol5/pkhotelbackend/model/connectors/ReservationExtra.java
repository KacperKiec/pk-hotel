package edu.zespol5.pkhotelbackend.model.connectors;

import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservation_extra")
@Setter
@Getter
@IdClass(ReservationExtraId.class)
public class ReservationExtra {
    @Id
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Id
    @ManyToOne
    @JoinColumn(name = "extra_id")
    private Extra extra;
}
