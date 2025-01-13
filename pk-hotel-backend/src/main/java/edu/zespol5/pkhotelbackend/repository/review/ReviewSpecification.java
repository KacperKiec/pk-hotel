package edu.zespol5.pkhotelbackend.repository.review;

import edu.zespol5.pkhotelbackend.model.review.Review;
import org.springframework.data.jpa.domain.Specification;

/**
 * The {@code ReviewSpecification} class contains static methods to create {@link Specification}
 * objects for filtering {@link Review} entities based on various criteria. Specifications
 * are used to construct dynamic queries in the repository layer, enabling flexible querying
 * capabilities for review-related data.
 *
 * <p>
 * The class provides specifications for filtering reviews by hotel, client email, and rating
 * (both greater than and less than a specified value).
 * </p>
 *
 * @see Specification
 * @see Review
 */
public class ReviewSpecification {

    /**
     * Creates a specification to filter reviews by the hotel ID.
     *
     * @param hotelId the hotel ID to filter by
     * @return a {@link Specification} that filters reviews by the hotel ID
     */
    public static Specification<Review> hasHotel(Integer hotelId){
        return (root, query, builder) -> {
            if(hotelId == null){
                return builder.conjunction();
            }
            return builder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    /**
     * Creates a specification to filter reviews by the client's email.
     *
     * @param email the email of the client to filter reviews by
     * @return a {@link Specification} that filters reviews by the client's email
     */
    public static Specification<Review> hasClient(String email){
        return (root, query, builder) -> {
            if(email == null || email.isEmpty()){
                return builder.conjunction();
            }
            return builder.equal(root.get("user").get("email"), email);
        };
    }

    /**
     * Creates a specification to filter reviews with a rating greater than or equal to the specified value.
     *
     * @param rating the rating value to filter by
     * @return a {@link Specification} that filters reviews by a minimum rating
     */
    public static Specification<Review> hasRatingGreaterThan(Integer rating){
        return (root, query, builder) -> {
            if(rating == null){
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    /**
     * Creates a specification to filter reviews with a rating less than or equal to the specified value.
     *
     * @param rating the rating value to filter by
     * @return a {@link Specification} that filters reviews by a maximum rating
     */
    public static Specification<Review> hasRatingLessThan(Integer rating){
        return (root, query, builder) -> {
            if(rating == null){
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("rating"), rating);
        };
    }
}
