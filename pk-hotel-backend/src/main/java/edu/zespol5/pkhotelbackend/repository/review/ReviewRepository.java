package edu.zespol5.pkhotelbackend.repository.review;

import edu.zespol5.pkhotelbackend.model.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findReviewById(int id);
    Page<Review> findAll(Pageable pageable);
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);
    void deleteById(int id);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotel.id = :hotelId")
    Double findAverageRatingByHotelId(@Param("hotelId") Integer hotelId);
}
