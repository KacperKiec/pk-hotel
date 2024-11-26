package edu.zespol5.pkhotelbackend.hotel;

import edu.zespol5.pkhotelbackend.review.Review;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> hasName(String name){
        return (root, query, builder) -> {
            if(name == null){
                return builder.conjunction();
            }
            return builder.equal(root.get("name"), name);
        };
    }

    public static Specification<Hotel> hasCountry(List<String> countries){
        return (root, query, builder) -> {
            if(countries == null || countries.isEmpty()){
                return builder.conjunction();
            }
            return root.get("country").in(countries);
        };
    }

    public static Specification<Hotel> hasCity(List<String> cities){
        return (root, query, builder) -> {
            if(cities == null || cities.isEmpty()){
                return builder.conjunction();
            }
            return root.get("city").in(cities);
        };
    }

    public static Specification<Hotel> hasRatingHigherOrEqual(Integer rating){
        return (root, query, builder) -> {
            if(rating == null || rating < 0){
                return builder.conjunction();
            }

            assert query != null;
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Review> reviewRoot = subquery.from(Review.class);

            Expression<Double> avgRating = builder.avg(reviewRoot.get("rating"));
            subquery.select(avgRating)
                    .where(builder.equal(reviewRoot.get("hotel"), root));

            return builder.greaterThanOrEqualTo(subquery, rating.doubleValue());
        };
    }

    public static Specification<Hotel> hasRatingLowerOrEqual(Integer rating){
        return (root, query, builder) -> {
            if(rating == null || rating < 0){
                return builder.conjunction();
            }

            assert query != null;
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Review> reviewRoot = subquery.from(Review.class);

            Expression<Double> avgRating = builder.avg(reviewRoot.get("rating"));
            subquery.select(avgRating)
                    .where(builder.equal(reviewRoot.get("hotel"), root));

            return builder.lessThanOrEqualTo(subquery, rating.doubleValue());
        };
    }
}
