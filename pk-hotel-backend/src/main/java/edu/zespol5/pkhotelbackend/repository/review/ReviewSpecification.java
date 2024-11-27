package edu.zespol5.pkhotelbackend.repository.review;

import edu.zespol5.pkhotelbackend.model.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public static Specification<Review> hasHotel(Integer hotelId){
        return (root, query, builder) -> {
            if(hotelId == null){
                return builder.conjunction();
            }
            return builder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    public static Specification<Review> hasClient(Integer clientId){
        return (root, query, builder) -> {
            if(clientId == null){
                return builder.conjunction();
            }
            return builder.equal(root.get("client").get("id"), clientId);
        };
    }

    public static Specification<Review> hasRatingGreaterThan(Integer rating){
        return (root, query, builder) -> {
            if(rating == null){
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    public static Specification<Review> hasRatingLessThan(Integer rating){
        return (root, query, builder) -> {
            if(rating == null){
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("rating"), rating);
        };
    }
}
