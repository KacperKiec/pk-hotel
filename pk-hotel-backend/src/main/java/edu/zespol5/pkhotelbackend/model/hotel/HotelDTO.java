package edu.zespol5.pkhotelbackend.model.hotel;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for the {@link Hotel} entity.
 * <p>
 * This class is used to transfer hotel data between layers of the application, typically from the
 * backend to the frontend. It contains a subset of the information available in the {@link Hotel}
 * entity and is used to avoid exposing the full entity with its relationships.
 * </p>
 */
@Data
public class HotelDTO {

    /**
     * The unique identifier of the hotel.
     */
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
     * The average rating of the hotel.
     * <p>
     * This field can represent the average rating based on customer reviews or other factors.
     * </p>
     */
    private double rating;
}
