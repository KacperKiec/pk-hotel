package edu.zespol5.pkhotelbackend.reservation;

import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {

    public static Specification<Reservation> hasStatus(ReservationStatus status) {
        return (root, query, builder) -> {
            if(status == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<Reservation> hasHotelId(Integer hotelId){
        return (root, query, builder) -> {
            if(hotelId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join("room").get("hotel").get("id"), hotelId);
        };
    }

    public static Specification<Reservation> hasClientId(Integer clientId){
        return (root, query, builder) -> {
            if(clientId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join("client").get("id"), clientId);
        };
    }
}
