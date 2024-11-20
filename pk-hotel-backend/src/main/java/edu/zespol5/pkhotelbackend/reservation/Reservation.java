package edu.zespol5.pkhotelbackend.reservation;

import edu.zespol5.pkhotelbackend.client.Client;
import edu.zespol5.pkhotelbackend.room.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

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
}
