package edu.zespol5.pkhotelbackend.model.reservation_extra;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite key for the {@link ReservationExtra} entity.
 * <p>
 * This class is used to uniquely identify a {@link ReservationExtra} association by combining the IDs of the reservation
 * and the extra service/item. The key is embedded in the {@code reservation_extra} table to establish a many-to-many
 * relationship between reservations and extras.
 * </p>
 */
@Embeddable
@Getter
@Setter
public class ReservationExtraId implements Serializable {

    /**
     * The ID of the reservation associated with this extra.
     */
    private Integer reservationId;

    /**
     * The ID of the extra associated with this reservation.
     */
    private Integer extraId;

    /**
     * Compares this {@link ReservationExtraId} to another object for equality.
     * <p>
     * The comparison is based on the {@code reservationId} and {@code extraId} values.
     * </p>
     *
     * @param o the object to compare to
     * @return {@code true} if this object is equal to the given object, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationExtraId that = (ReservationExtraId) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(extraId, that.extraId);
    }

    /**
     * Computes a hash code for this {@link ReservationExtraId}.
     * <p>
     * The hash code is calculated based on the {@code reservationId} and {@code extraId} values.
     * </p>
     *
     * @return the hash code for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(reservationId, extraId);
    }
}
