package edu.zespol5.pkhotelbackend.model.room;

import edu.zespol5.pkhotelbackend.model.room_convenience.RoomConvenience;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.room_image.RoomImage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a room in a hotel.
 * <p>
 * This entity class is used to model a hotel room in the system, including its attributes such as room number,
 * hotel, capacity (number of places), price, room standard, and associated conveniences and images.
 * </p>
 */
@Getter
@Setter
@Entity
@Table(name = "room")
@IdClass(RoomId.class)
public class Room {

    /**
     * The unique identifier for the room, which is the room number within the hotel.
     */
    @Id
    @Column(name = "room_nr")
    private int roomNr;

    /**
     * The hotel to which this room belongs.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    /**
     * The number of places or capacity of the room.
     */
    private int places;

    /**
     * The price of the room per night.
     */
    private double price;

    /**
     * The standard of the room.
     */
    @Enumerated(EnumType.STRING)
    private RoomStandard standard;

    /**
     * A description of the room, providing additional details to potential guests.
     */
    private String description;

    /**
     * A set of conveniences (e.g., air conditioning, Wi-Fi) available in the room.
     */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomConvenience> conveniences = new HashSet<>();

    /**
     * A set of images associated with the room.
     */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomImage> images = new HashSet<>();

    /**
     * Default constructor.
     */
    public Room(){

    }

    /**
     * Adds a convenience to the room.
     *
     * @param roomConvenience The convenience to add.
     */
    public void addConvenience(RoomConvenience roomConvenience){
        conveniences.add(roomConvenience);
    }

    /**
     * Removes a convenience from the room.
     *
     * @param roomConvenience The convenience to remove.
     */
    public void removeConvenience(RoomConvenience roomConvenience){
        conveniences.remove(roomConvenience);
    }

    /**
     * Adds an image to the room.
     *
     * @param roomImage The image to add.
     */
    public void addImage(RoomImage roomImage){
        images.add(roomImage);
    }

    /**
     * Removes an image from the room.
     *
     * @param roomImage The image to remove.
     */
    public void removeImage(RoomImage roomImage){
        images.remove(roomImage);
    }
}
