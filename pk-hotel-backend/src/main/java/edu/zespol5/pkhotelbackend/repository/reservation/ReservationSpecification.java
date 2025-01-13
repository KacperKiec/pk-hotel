package edu.zespol5.pkhotelbackend.repository.reservation;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * The {@code ReservationSpecification} class provides static methods for creating {@link Specification}
 * objects to filter {@link Reservation} entities based on various criteria. Specifications allow dynamic
 * querying, enabling the creation of flexible and reusable queries for filtering reservations.
 *
 * <p>
 * This class provides specifications for filtering reservations by status, hotel ID, and client ID.
 * </p>
 *
 * @see Reservation
 * @see ReservationStatus
 * @see Specification
 */
public class ReservationSpecification {

    /**
     * Creates a specification to filter reservations by their status.
     *
     * @param status the status of the reservation to filter by
     * @return a {@link Specification} that filters reservations by status
     */
    public static Specification<Reservation> hasStatus(ReservationStatus status) {
        return (root, query, builder) -> {
            if(status == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("status"), status);
        };
    }

    /**
     * Creates a specification to filter reservations by the hotel ID associated with the reserved room.
     *
     * @param hotelId the hotel ID to filter reservations by
     * @return a {@link Specification} that filters reservations by hotel ID
     */
    public static Specification<Reservation> hasHotelId(Integer hotelId) {
        return (root, query, builder) -> {
            if(hotelId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join("room").get("hotel").get("id"), hotelId);
        };
    }

    /**
     * Creates a specification to filter reservations by the client ID who made the reservation.
     *
     * @param clientId the client ID to filter reservations by
     * @return a {@link Specification} that filters reservations by client ID
     */
    public static Specification<Reservation> hasClientId(Integer clientId) {
        return (root, query, builder) -> {
            if(clientId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join("user").get("id"), clientId);
        };
    }
}
