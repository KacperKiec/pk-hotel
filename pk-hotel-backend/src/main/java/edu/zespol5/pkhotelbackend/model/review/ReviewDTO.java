package edu.zespol5.pkhotelbackend.model.review;

import lombok.Data;

/**
 * Represents a Data Transfer Object (DTO) for a hotel review.
 * <p>
 * This class is used for transferring review data between different layers of the application, typically between
 * the service and the presentation layers. It contains the essential details about a review, including the hotel name,
 * user information, rating, and comment.
 * </p>
 */
@Data
public class ReviewDTO {

    /**
     * The unique identifier of the review.
     */
    private int id;

    /**
     * The name of the hotel that the review is associated with.
     */
    private String hotelName;

    /**
     * The first name of the user who wrote the review.
     */
    private String userFirstName;

    /**
     * The last name of the user who wrote the review.
     */
    private String userLastName;

    /**
     * The rating given by the user in the review. Typically, a numerical value (e.g., from 1 to 5).
     */
    private int rating;

    /**
     * The textual comment or content of the review provided by the user.
     */
    private String comment;
}
