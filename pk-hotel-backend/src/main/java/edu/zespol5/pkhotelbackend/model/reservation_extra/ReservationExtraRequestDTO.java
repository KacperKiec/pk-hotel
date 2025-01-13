package edu.zespol5.pkhotelbackend.model.reservation_extra;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import lombok.Data;

import java.util.List;

/**
 * DTO (Data Transfer Object) used for creating or updating the extras associated with a reservation.
 * <p>
 * This class is used to transfer data related to the reservation and its associated extras (services or items) in a
 * request to the server. It contains the reservation details and a list of IDs for the extra services/items to be added
 * to the reservation.
 * </p>
 */
@Data
public class ReservationExtraRequestDTO {

    /**
     * The reservation that the extras will be associated with.
     */
    private Reservation reservation;

    /**
     * A list of extra service/item IDs to be associated with the reservation.
     * These services/items are typically optional, such as additional bed, parking, etc.
     */
    private List<Integer> extraIds;
}
