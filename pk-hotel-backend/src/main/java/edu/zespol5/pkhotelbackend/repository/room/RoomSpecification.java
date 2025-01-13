package edu.zespol5.pkhotelbackend.repository.room;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationStatus;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomStandard;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

/**
 * The {@code RoomSpecification} class provides various {@link Specification}s
 * for filtering {@link Room} entities in the repository. It contains static methods
 * to create dynamic queries based on different filtering criteria such as hotel ID,
 * room number, price, and room standard. Additionally, it includes a method to
 * check room availability based on reservation dates.
 *
 * <p>
 * These specifications are used to filter rooms when querying the database using
 * the Spring Data JPA repository.
 * </p>
 *
 * @see Room
 * @see Specification
 * @see Reservation
 */
public class RoomSpecification {

    /**
     * Creates a specification to filter rooms based on hotel ID.
     *
     * @param hotelId the hotel ID to filter by
     * @return a {@link Specification} that filters rooms by hotel ID
     */
    public static Specification<Room> hasHotelId(Integer hotelId) {
        return (root, query, builder) -> {
            if(hotelId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    /**
     * Creates a specification to filter rooms based on room number.
     *
     * @param roomNr the room number to filter by
     * @return a {@link Specification} that filters rooms by room number
     */
    public static Specification<Room> hasRoomNr(Integer roomNr) {
        return (root, query, builder) -> {
            if(roomNr == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("roomNr"), roomNr);
        };
    }

    /**
     * Creates a specification to filter rooms based on city.
     *
     * @param cities a list of cities to filter by
     * @return a {@link Specification} that filters rooms by city
     */
    public static Specification<Room> hasCity(List<String> cities) {
        return (root, query, builder) -> {
            if(cities == null || cities.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("hotel").get("city").in(cities);
        };
    }

    /**
     * Creates a specification to filter rooms based on country.
     *
     * @param counties a list of countries to filter by
     * @return a {@link Specification} that filters rooms by country
     */
    public static Specification<Room> hasCountry(List<String> counties) {
        return (root, query, builder) -> {
            if(counties == null || counties.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("hotel").get("country").in(counties);
        };
    }

    /**
     * Creates a specification to filter rooms where the price is greater than or equal to a given value.
     *
     * @param price the price threshold
     * @return a {@link Specification} that filters rooms by price
     */
    public static Specification<Room> hasPriceGreaterOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

    /**
     * Creates a specification to filter rooms where the price is less than or equal to a given value.
     *
     * @param price the price threshold
     * @return a {@link Specification} that filters rooms by price
     */
    public static Specification<Room> hasPriceLessOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("price"), price);
        };
    }

    /**
     * Creates a specification to filter rooms based on room standard.
     *
     * @param standard the room standard to filter by
     * @return a {@link Specification} that filters rooms by standard
     */
    public static Specification<Room> hasStandard(RoomStandard standard) {
        return (root, query, builder) -> {
            if(standard == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("standard"), standard);
        };
    }

    /**
     * Creates a specification to filter rooms based on the number of places.
     *
     * @param places the number of places in the room
     * @return a {@link Specification} that filters rooms by the number of places
     */
    public static Specification<Room> hasPlaces(Integer places) {
        return (root, query, builder) -> {
            if(places == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("places"), places);
        };
    }

    /**
     * Creates a specification to filter rooms based on availability during a given date range.
     *
     * @param startDate the start date of the reservation period
     * @param endDate the end date of the reservation period
     * @return a {@link Specification} that filters rooms by availability
     */
    public static Specification<Room> isAvailable(LocalDate startDate, LocalDate endDate) {
        return (root, query, builder) -> {
            if (startDate == null || endDate == null) {
                return builder.conjunction();
            }

            assert query != null;
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);

            Predicate sameRoom = builder.equal(reservation.get("room"), root);

            Predicate startsBeforeEnd = builder.lessThanOrEqualTo(reservation.get("checkInDate"), endDate);
            Predicate endsAfterStart = builder.greaterThanOrEqualTo(reservation.get("checkOutDate"), startDate);

            Predicate isNotCancelled = builder.notEqual(reservation.get("status"), ReservationStatus.CANCELED);

            Predicate overlappingReservation = builder.and(sameRoom, startsBeforeEnd, endsAfterStart, isNotCancelled);

            subquery.select(reservation.get("id")).where(overlappingReservation);

            return builder.not(builder.exists(subquery));
        };
    }
}
