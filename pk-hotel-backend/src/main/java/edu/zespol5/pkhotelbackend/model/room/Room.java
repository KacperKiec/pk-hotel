package edu.zespol5.pkhotelbackend.model.room;

import edu.zespol5.pkhotelbackend.model.room_convenience.RoomConvenience;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.room_image.RoomImage;
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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomImage> images = new HashSet<>();

    public Room(){

    }

    public void addConvenience(RoomConvenience roomConvenience){
        conveniences.add(roomConvenience);
    }

    public void removeConvenience(RoomConvenience roomConvenience){
        conveniences.remove(roomConvenience);
    }

    public void addImage(RoomImage roomImage){
        images.add(roomImage);
    }

    public void removeImage(RoomImage roomImage){
        images.remove(roomImage);
    }
}
