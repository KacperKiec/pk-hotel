package edu.zespol5.pkhotelbackend.model.hotel;

import edu.zespol5.pkhotelbackend.model.room.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a hotel entity in the system.
 * <p>
 * A hotel has an associated set of rooms and basic details like name, owner, registration date,
 * and address. This class is mapped to a database table called "hotel".
 * </p>
 *
 * @see Room
 */
@Getter
@Setter
@Entity
@Table(name = "hotel")
public class Hotel {

    /**
     * The unique identifier of the hotel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the hotel.
     */
    private String name;

    /**
     * The owner of the hotel.
     */
    private String owner;

    /**
     * The registration date of the hotel.
     */
    private LocalDate registerDate;

    /**
     * The country where the hotel is located.
     */
    private String country;

    /**
     * The city where the hotel is located.
     */
    private String city;

    /**
     * The address of the hotel.
     */
    private String address;

    /**
     * The set of rooms associated with this hotel.
     * <p>
     * This is a one-to-many relationship with the {@link Room} entity, and rooms are automatically
     * deleted if the hotel is removed.
     * </p>
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    /**
     * Default constructor for Hotel.
     */
    public Hotel() {
        // Default constructor
    }

    /**
     * Adds a room to the hotel.
     *
     * @param room the room to be added to the hotel
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Removes a room from the hotel.
     *
     * @param room the room to be removed from the hotel
     */
    public void removeRoom(Room room) {
        rooms.remove(room);
    }
}
