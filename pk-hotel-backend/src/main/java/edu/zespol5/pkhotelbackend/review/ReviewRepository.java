package edu.zespol5.pkhotelbackend.review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);
    List<Review> findAll();
    Optional<Review> findReviewById(int id);
}
