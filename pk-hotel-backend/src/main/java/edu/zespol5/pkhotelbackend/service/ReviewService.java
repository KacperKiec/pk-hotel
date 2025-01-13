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

/**
 * {@code ReviewService} is a service that manages operations related to hotel reviews.
 * It allows adding, retrieving, and filtering reviews for hotels based on various criteria.
 *
 * <p>
 * This service includes the following functionalities:
 * <ul>
 *   <li>Adding a review for a hotel, associating the review with a user and hotel.</li>
 *   <li>Retrieving a review by its ID.</li>
 *   <li>Getting all reviews with pagination support.</li>
 *   <li>Filtering reviews by hotel ID, client email, and rating range with pagination support.</li>
 * </ul>
 * </p>
 *
 * @see Review
 * @see ReviewDTO
 * @see ReviewRepository
 * @see HotelRepository
 * @see UserRepository
 */
@Service
public class ReviewService {

    private final ReviewRepository repository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new {@code ReviewService} with the provided {@link ReviewRepository},
     * {@link HotelRepository}, and {@link UserRepository}.
     *
     * @param repository the {@link ReviewRepository} used to access and manage review data.
     * @param hotelRepository the {@link HotelRepository} used to access hotel data.
     * @param userRepository the {@link UserRepository} used to access user data.
     */
    public ReviewService(ReviewRepository repository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.repository = repository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adds a review for a hotel, associating the review with a user and a hotel.
     * The user is validated by email, and the hotel is validated by ID.
     *
     * @param review the review to be added.
     * @param email the email of the user who is adding the review.
     * @return a {@link ReviewDTO} containing the details of the added review.
     * @throws UserNotFoundException if the user with the provided email does not exist.
     * @throws HotelNotFoundException if the hotel with the provided ID does not exist.
     * @throws IllegalOperationException if the user's email does not match the review's user.
     */
    public ReviewDTO addReview(Review review, String email) {

        var existingUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + review.getUser().getEmail() + " not found")
        );

        review.setUser(existingUser);

        if (!existingUser.getEmail().equals(email)) {
            throw new IllegalOperationException("User incompatibility");
        }

        var existingHotel = hotelRepository.findHotelById(review.getHotel().getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );

        review.setHotel(existingHotel);

        return toDTO(repository.save(review));
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id the ID of the review to retrieve.
     * @return the {@link Review} object associated with the given ID.
     * @throws ReviewNotFoundException if the review with the provided ID does not exist.
     */
    public Review getReviewById(int id) {
        return repository.findReviewById(id).orElseThrow(
                () -> new ReviewNotFoundException("Review with id " + id + " was not found")
        );
    }

    /**
     * Retrieves all reviews with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link ReviewDTO} objects.
     */
    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Filters reviews based on hotel ID, client email, and a rating range, with pagination support.
     *
     * @param hotelId the ID of the hotel to filter reviews by.
     * @param clientEmail the email of the client to filter reviews by.
     * @param upperRatingLimit the upper limit of the rating range.
     * @param lowerRatingLimit the lower limit of the rating range.
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link ReviewDTO} objects matching the specified criteria.
     */
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

    /**
     * Converts a {@link Review} to a {@link ReviewDTO}.
     *
     * @param review the review to convert.
     * @return the converted {@link ReviewDTO}.
     */
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
