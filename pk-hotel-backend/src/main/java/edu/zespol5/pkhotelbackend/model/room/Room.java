package edu.zespol5.pkhotelbackend.model.room;

import edu.zespol5.pkhotelbackend.model.connectors.RoomConvenience;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomConvenience> conveniences = new HashSet<>();

    public Room(){

    }
}