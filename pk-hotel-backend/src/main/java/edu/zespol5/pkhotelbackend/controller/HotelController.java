package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.model.hotel.HotelDTO;
import edu.zespol5.pkhotelbackend.model.review.Review;
import edu.zespol5.pkhotelbackend.model.review.ReviewDTO;
import edu.zespol5.pkhotelbackend.service.HotelService;
import edu.zespol5.pkhotelbackend.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for handling hotel-related requests, including searching hotels,
 * retrieving hotel details, and managing reviews.
 * <p>
 * This class provides the endpoints for interacting with hotel data and reviews. It allows
 * clients to search for hotels, view details of a specific hotel, and add/retrieve reviews.
 * </p>
 */
@Controller
@RequestMapping(value = "/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final ReviewService reviewService;

    /**
     * Constructor for initializing the controller with the given hotel and review services.
     *
     * @param hotelService the service responsible for hotel operations
     * @param reviewService the service responsible for review operations
     */
    public HotelController(HotelService hotelService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<HotelDTO>> getHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> countries,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) Integer lowerRatingLimit,
            @RequestParam(required = false) Integer upperRatingLimit,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = hotelService.getHotelsBy(name, countries, cities, lowerRatingLimit, upperRatingLimit, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable int id) {
        var result = hotelService.getHotelById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/review")
    public ResponseEntity<ReviewDTO> addReview(Authentication auth, @RequestBody Review review) {
        var result = reviewService.addReview(review, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(value = "/review/{id}")
    public ResponseEntity<Page<ReviewDTO>> getReviews(@RequestParam(defaultValue = "0") int page, @PathVariable int id) {
        Pageable pageable = PageRequest.of(page, 10);
        var result = reviewService.getReviewsBy(id, null, null, null, pageable);
        return ResponseEntity.ok(result);
    }
}
