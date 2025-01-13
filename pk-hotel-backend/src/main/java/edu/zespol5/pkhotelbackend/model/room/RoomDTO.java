package edu.zespol5.pkhotelbackend.model.room;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.Image;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a room in a hotel.
 * <p>
 * This class is used to transfer room information between layers (e.g., from the backend to the frontend).
 * It contains details such as room number, hotel name, number of places, price, standard, description, conveniences,
 * rating, and images.
 * </p>
 */
@Data
public class RoomDTO {

    /**
     * The unique identifier for the room, which is the room number.
     */
    private int roomNr;

    /**
     * The name of the hotel where the room is located.
     */
    private String hotelName;

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
    private RoomStandard standard;

    /**
     * A description of the room, providing additional details to potential guests.
     */
    private String description;

    /**
     * A list of conveniences available in the room (e.g., Wi-Fi, air conditioning).
     */
    private List<Convenience> conveniences;

    /**
     * The rating of the room based on customer reviews.
     */
    private Double rating;

    /**
     * A list of images associated with the room.
     */
    private List<Image> images;
}
