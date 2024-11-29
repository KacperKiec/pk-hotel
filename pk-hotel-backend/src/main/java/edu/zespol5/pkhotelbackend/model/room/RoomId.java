package edu.zespol5.pkhotelbackend.model.room;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Setter
@Getter
public class RoomId implements Serializable {
    private int hotel;
    private int roomNr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId that = (RoomId) o;
        return Objects.equals(getRoomNr(), that.getRoomNr()) && Objects.equals(getHotel(), that.getHotel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomNr(), getHotel());
    }

}
