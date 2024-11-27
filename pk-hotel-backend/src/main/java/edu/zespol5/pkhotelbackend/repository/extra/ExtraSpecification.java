package edu.zespol5.pkhotelbackend.repository.extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import org.springframework.data.jpa.domain.Specification;

public class ExtraSpecification {

    public static Specification<Extra> hasName(String name) {
        return (root, query, builder) -> {
            if(name == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("name"), name);
        };
    }

    public static Specification<Extra> hasPriceGreaterOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("pricePerDay"), price);
        };
    }

    public static Specification<Extra> hasPriceLessOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("pricePerDay"), price);
        };
    }
}
