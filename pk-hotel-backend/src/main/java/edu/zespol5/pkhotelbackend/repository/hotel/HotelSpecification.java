package edu.zespol5.pkhotelbackend.repository.hotel;

import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.review.Review;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The {@code HotelSpecification} class provides static methods to construct JPA specifications
 * for querying {@link Hotel} entities based on various dynamic criteria. These specifications
 * enable flexible and reusable query conditions.
 *
 * <p>
 * The specifications include filters for hotel properties such as name, location, and ratings.
 * </p>
 *
 * @see Hotel
 * @see Specification
 */
public class HotelSpecification {

    /**
     * Creates a specification to filter {@link Hotel} entities by their name.
     *
     * @param name the name of the hotel to match; if {@code null}, matches all hotels
     * @return a specification that matches hotels with the given name
     */
    public static Specification<Hotel> hasName(String name) {
        return (root, query, builder) -> {
            if (name == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("name"), name);
        };
    }

    /**
     * Creates a specification to filter {@link Hotel} entities by their country.
     *
     * @param countries a list of countries to match; if {@code null} or empty, matches all hotels
     * @return a specification that matches hotels in the specified countries
     */
    public static Specification<Hotel> hasCountry(List<String> countries) {
        return (root, query, builder) -> {
            if (countries == null || countries.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("country").in(countries);
        };
    }

    /**
     * Creates a specification to filter {@link Hotel} entities by their city.
     *
     * @param cities a list of cities to match; if {@code null} or empty, matches all hotels
     * @return a specification that matches hotels in the specified cities
     */
    public static Specification<Hotel> hasCity(List<String> cities) {
        return (root, query, builder) -> {
            if (cities == null || cities.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("city").in(cities);
        };
    }

    /**
     * Creates a specification to filter {@link Hotel} entities with an average rating
     * greater than or equal to the specified value.
     *
     * @param rating the minimum average rating to match; if {@code null} or negative,
     *               matches all hotels
     * @return a specification that matches hotels with an average rating above or equal to
     *         the given value
     */
    public static Specification<Hotel> hasRatingHigherOrEqual(Integer rating) {
        return (root, query, builder) -> {
            if (rating == null || rating < 0) {
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

    /**
     * Creates a specification to filter {@link Hotel} entities with an average rating
     * less than or equal to the specified value.
     *
     * @param rating the maximum average rating to match; if {@code null} or negative,
     *               matches all hotels
     * @return a specification that matches hotels with an average rating below or equal to
     *         the given value
     */
    public static Specification<Hotel> hasRatingLowerOrEqual(Integer rating) {
        return (root, query, builder) -> {
            if (rating == null || rating < 0) {
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
