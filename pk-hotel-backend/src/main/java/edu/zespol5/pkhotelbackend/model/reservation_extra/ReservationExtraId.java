package edu.zespol5.pkhotelbackend.model.reservation_extra;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ReservationExtraId implements Serializable {
    private Integer reservationId;
    private Integer extraId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationExtraId that = (ReservationExtraId) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(extraId, that.extraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, extraId);
    }
}
