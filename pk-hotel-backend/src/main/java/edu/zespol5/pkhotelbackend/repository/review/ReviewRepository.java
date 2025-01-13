package edu.zespol5.pkhotelbackend.repository.review;

import edu.zespol5.pkhotelbackend.model.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ReviewRepository} interface provides data access operations for the
 * {@link Review} entity. It supports standard CRUD operations, dynamic queries through
 * specifications, and custom queries for specific use cases such as calculating average ratings.
 *
 * <p>
 * This interface allows saving, retrieving, updating, and deleting reviews for hotels.
 * It also supports querying reviews with pagination and filtering by dynamic criteria.
 * </p>
 *
 * @see Review
 */
public interface ReviewRepository {

    /**
     * Saves a given {@link Review} entity to the database.
     *
     * @param review the review entity to save
     * @return the saved review entity
     */
    Review save(Review review);

    /**
     * Finds a {@link Review} entity by its unique ID.
     *
     * @param id the ID of the review
     * @return an {@link Optional} containing the found review, or empty if not found
     */
    Optional<Review> findReviewById(int id);

    /**
     * Retrieves a paginated list of all {@link Review} entities.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of all reviews
     */
    Page<Review> findAll(Pageable pageable);

    /**
     * Retrieves a paginated list of all {@link Review} entities that match the given
     * dynamic filtering criteria.
     *
     * @param spec the specification defining dynamic filtering conditions
     * @param pageable the pagination information
     * @return a {@link Page} of reviews matching the given specification
     */
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);

    /**
     * Deletes a {@link Review} entity by its unique ID.
     *
     * @param id the ID of the review to delete
     */
    void deleteById(int id);

    /**
     * Finds the average rating for all reviews of a hotel by its hotel ID.
     *
     * @param hotelId the ID of the hotel to calculate the average rating for
     * @return the average rating of the hotel, or {@code null} if no reviews exist
     */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotel.id = :hotelId")
    Double findAverageRatingByHotelId(@Param("hotelId") Integer hotelId);
}
