package edu.zespol5.pkhotelbackend.model.reservation;

import edu.zespol5.pkhotelbackend.model.connectors.ReservationExtra;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id"),
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr")
    })
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ReservationExtra> extras = new HashSet<>();

    public void addExtra(ReservationExtra extra) {
        extras.add(extra);
    }

    public void removeExtra(ReservationExtra extra) {
        extras.remove(extra);
    }
}
