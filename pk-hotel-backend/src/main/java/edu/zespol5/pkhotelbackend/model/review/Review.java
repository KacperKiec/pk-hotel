package edu.zespol5.pkhotelbackend.model.review;

import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a review for a hotel written by a user.
 * <p>
 * This class is an entity that stores the information about a user's review of a hotel, including the rating
 * and the content of the review. It is mapped to the "review" table in the database.
 * </p>
 */
@Data
@Entity
@Table(name = "review")
public class Review {

    /**
     * Unique identifier for the review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The hotel that the review is associated with.
     * This is a many-to-one relationship, as each review is related to one hotel.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    /**
     * The user who wrote the review.
     * This is a many-to-one relationship, as each review is written by one user.
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    /**
     * The rating given by the user for the hotel. Typically, this would be a numerical value (e.g., from 1 to 5).
     */
    private int rating;

    /**
     * The content of the review, where the user can write their feedback about the hotel.
     */
    private String content;
}
