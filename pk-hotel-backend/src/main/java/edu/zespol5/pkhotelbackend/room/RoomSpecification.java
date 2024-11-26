package edu.zespol5.pkhotelbackend.room;

import edu.zespol5.pkhotelbackend.reservation.Reservation;
import edu.zespol5.pkhotelbackend.reservation.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RoomSpecification {

    public static Specification<Room> hasHotelId(Integer hotelId) {
        return (root, query, builder) -> {
            if(hotelId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    public static Specification<Room> hasPriceGreaterOrEqualThan(Integer price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Room> hasPriceLessOrEqualThan(Integer price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Room> hasStandard(RoomStandard standard){
        return (root, query, builder) -> {
            if(standard == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("standard"), standard);
        };
    }

    public static Specification<Room> hasPlaces(Integer places) {
        return (root, query, builder) -> {
            if(places == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("places"), places);
        };
    }

    public static Specification<Room> isAvailable(LocalDate startDate, LocalDate endDate) {
        return (root, query, builder) -> {
            if(startDate == null || endDate == null) {
                return builder.conjunction();
            }
            assert query != null;

            return builder.not(
                builder.exists(
                    query.subquery(Reservation.class)
                        .select(query.from(Reservation.class))
                        .where(
                            builder.equal(query.from(Reservation.class).get("room"), root),
                            builder.or(
                                builder.and(
                                    builder.lessThanOrEqualTo(query.from(Reservation.class).get("checkInDate"), endDate),
                                    builder.greaterThanOrEqualTo(query.from(Reservation.class).get("checkOutDate"), startDate)
                                ),
                                builder.and(
                                    builder.greaterThanOrEqualTo(query.from(Reservation.class).get("checkInDate"), startDate),
                                    builder.lessThanOrEqualTo(query.from(Reservation.class).get("checkOutDate"), endDate)
                                )
                            ),
                            builder.notEqual(query.from(Reservation.class).get("status"), ReservationStatus.CANCELED)
                        )
                )
            );
        };
    }
}
