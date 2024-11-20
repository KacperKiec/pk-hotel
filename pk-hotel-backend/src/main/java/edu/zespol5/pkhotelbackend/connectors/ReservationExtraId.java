package edu.zespol5.pkhotelbackend.connectors;

import java.io.Serializable;
import java.util.Objects;

public class ReservationExtraId implements Serializable {
    private int reservation;
    private int extra;

    public ReservationExtraId(){

    }

    public ReservationExtraId(int reservation, int extra) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationExtraId that = (ReservationExtraId) o;
        return Objects.equals(reservation, that.reservation) &&
                Objects.equals(extra, that.extra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, extra);
    }
}
