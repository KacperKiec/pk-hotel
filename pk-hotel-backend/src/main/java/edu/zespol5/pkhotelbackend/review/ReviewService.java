package edu.zespol5.pkhotelbackend.review;

import edu.zespol5.pkhotelbackend.client.ClientNotFoundException;
import edu.zespol5.pkhotelbackend.client.ClientRepository;
import edu.zespol5.pkhotelbackend.hotel.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.hotel.HotelRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final HotelRepository hotelRepository;
    private final ClientRepository clientRepository;

    public ReviewService(ReviewRepository repository, HotelRepository hotelRepository, ClientRepository clientRepository) {
        this.repository = repository;
        this.hotelRepository = hotelRepository;
        this.clientRepository = clientRepository;
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

        clientRepository.findClientById(clientId).orElseThrow(
                () -> new ClientNotFoundException("Client with id " + clientId + " was not found")
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
