package edu.zespol5.pkhotelbackend.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents an extra service or feature that can be added to a reservation.
 * <p>
 * The `Extra` class is used to define optional extra services or features, such as breakfast, parking, or spa services,
 * that can be added to a hotel room reservation. Each extra has a unique identifier, a name, and a price per day.
 * </p>
 */
@Data
@Entity
@Table(name = "extra")
public class Extra {

    /**
     * Unique identifier for the extra service.
     * This is the primary key used to uniquely identify the extra in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the extra service.
     * Examples include services like "Breakfast", "Parking", or "Spa".
     */
    private String name;

    /**
     * The price per day for the extra service.
     * This represents the additional cost a customer must pay per day for the extra service, such as for breakfast or parking.
     */
    private Double pricePerDay;
}
