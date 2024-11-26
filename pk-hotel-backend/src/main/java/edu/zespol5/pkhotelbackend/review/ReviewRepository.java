package edu.zespol5.pkhotelbackend.review;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findReviewById(int id);
    List<Review> findAll();
    List<Review> findAll(Specification<Review> spec);
}
