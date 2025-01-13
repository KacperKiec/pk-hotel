package edu.zespol5.pkhotelbackend.repository.extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import org.springframework.data.jpa.domain.Specification;

/**
 * The {@code ExtraSpecification} class provides static methods to build JPA specifications
 * for querying {@link Extra} entities based on dynamic criteria. These specifications can
 * be combined to create complex query filters.
 *
 * <p>
 * This class is used to define reusable query conditions for filtering extras by
 * properties such as name and price.
 * </p>
 *
 * @see Extra
 * @see Specification
 */
public class ExtraSpecification {

    /**
     * Creates a specification to filter {@link Extra} entities by their name.
     *
     * @param name the name of the extra to match; if {@code null}, the specification will
     *             match all entities
     * @return a specification that matches extras with the given name, or matches all
     *         if {@code name} is {@code null}
     */
    public static Specification<Extra> hasName(String name) {
        return (root, query, builder) -> {
            if (name == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("name"), name);
        };
    }

    /**
     * Creates a specification to filter {@link Extra} entities with a price greater than
     * or equal to the specified value.
     *
     * @param price the minimum price of the extra; if {@code null}, the specification
     *              will match all entities
     * @return a specification that matches extras with a price greater than or equal
     *         to the given value, or matches all if {@code price} is {@code null}
     */
    public static Specification<Extra> hasPriceGreaterOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if (price == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("pricePerDay"), price);
        };
    }

    /**
     * Creates a specification to filter {@link Extra} entities with a price less than
     * or equal to the specified value.
     *
     * @param price the maximum price of the extra; if {@code null}, the specification
     *              will match all entities
     * @return a specification that matches extras with a price less than or equal to
     *         the given value, or matches all if {@code price} is {@code null}
     */
    public static Specification<Extra> hasPriceLessOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if (price == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("pricePerDay"), price);
        };
    }
}
