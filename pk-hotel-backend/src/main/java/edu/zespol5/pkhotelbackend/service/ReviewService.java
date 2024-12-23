package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.IllegalOperationException;
import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.model.review.ReviewDTO;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.review.Review;
import edu.zespol5.pkhotelbackend.exception.ReviewNotFoundException;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.review.ReviewSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public ReviewDTO addReview(Review review, String email) {

        var existingUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + review.getUser().getEmail() + " not found")
        );

        review.setUser(existingUser);

        if(!existingUser.getEmail().equals(email)) {
            throw new IllegalOperationException("User incompatibility");
        }

        var existingHotel = hotelRepository.findHotelById(review.getHotel().getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );

        review.setHotel(existingHotel);

        return toDTO(repository.save(review));
    }

    public Review getReviewById(int id) {
        return repository.findReviewById(id).orElseThrow(
                () -> new ReviewNotFoundException("Review with id " + id + " was not found")
        );
    }

    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    public Page<ReviewDTO> getReviewsBy(
            Integer hotelId,
            String clientEmail,
            Integer upperRatingLimit,
            Integer lowerRatingLimit,
            Pageable pageable) {

        Specification<Review> spec = Specification.where(ReviewSpecification.hasClient(clientEmail))
                .and(ReviewSpecification.hasHotel(hotelId))
                .and(ReviewSpecification.hasRatingGreaterThan(lowerRatingLimit))
                .and(ReviewSpecification.hasRatingLessThan(upperRatingLimit));
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getContent());
        dto.setUserFirstName(review.getUser().getFirstName());
        dto.setUserLastName(review.getUser().getLastName());
        dto.setHotelName(review.getHotel().getName());
        return dto;
    }
}
