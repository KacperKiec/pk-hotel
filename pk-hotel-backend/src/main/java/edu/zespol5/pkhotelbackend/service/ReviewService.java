package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.Review;
import edu.zespol5.pkhotelbackend.exception.ReviewNotFoundException;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.review.ReviewSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository repository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.repository = repository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    public Review save(Review review) {
        return repository.save(review);
    }

    public Review getReviewById(int id) {
        return repository.findReviewById(id).orElseThrow(
                () -> new ReviewNotFoundException("Review with id " + id + " was not found")
        );
    }

    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    public List<Review> getReviewsBy(
            int hotelId,
            int clientId,
            int upperRatingLimit,
            int lowerRatingLimit) {

        userRepository.findClientById(clientId).orElseThrow(
                () -> new UserNotFoundException("Client with id " + clientId + " was not found")
        );

        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );

        Specification<Review> spec = Specification.where(ReviewSpecification.hasClient(clientId))
                .and(ReviewSpecification.hasHotel(hotelId))
                .and(ReviewSpecification.hasRatingGreaterThan(lowerRatingLimit))
                .and(ReviewSpecification.hasRatingLessThan(upperRatingLimit));
        return repository.findAll(spec);
    }
}
