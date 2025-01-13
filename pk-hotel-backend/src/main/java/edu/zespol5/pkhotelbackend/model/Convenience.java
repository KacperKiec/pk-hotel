package edu.zespol5.pkhotelbackend.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a convenience feature available for a room or hotel.
 * <p>
 * The `Convenience` class is used to define various conveniences (e.g., Wi-Fi, air conditioning) that can be associated with rooms or hotels.
 * Each convenience has a unique identifier and a name.
 * </p>
 */
@Data
@Entity
@Table(name = "convenience")
public class Convenience {

    /**
     * Unique identifier for the convenience.
     * This is the primary key used to uniquely identify the convenience in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the convenience.
     * This could be something like "Wi-Fi", "Air Conditioning", or other features offered by a room or hotel.
     */
    private String name;
}
