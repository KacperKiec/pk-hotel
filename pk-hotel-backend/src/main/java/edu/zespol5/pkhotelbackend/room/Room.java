package edu.zespol5.pkhotelbackend.room;

import edu.zespol5.pkhotelbackend.hotel.Hotel;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room")
@IdClass(RoomId.class)
public class Room {

    @Id
    @Column(name = "room_nr")
    private int roomNr;

    @Id
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private int places;
    private double price;

    @Enumerated(EnumType.STRING)
    private RoomStandard standard;
    private String description;

    public Room(){

    }
}
