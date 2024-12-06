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

public class RoomSpecification {

    public static Specification<Room> hasHotelId(Integer hotelId) {
        return (root, query, builder) -> {
            if(hotelId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    public static Specification<Room> hasRoomNr(Integer roomNr) {
        return (root, query, builder) -> {
            if(roomNr == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("roomNr"), roomNr);
        };
    }

    public static Specification<Room> hasPriceGreaterOrEqualThan(Double price) {
        return (root, query, builder) -> {
            if(price == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Room> hasPriceLessOrEqualThan(Double price) {
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
