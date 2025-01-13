package edu.zespol5.pkhotelbackend.model.room;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing the composite key for the Room entity.
 * <p>
 * This class is used as a composite primary key for the Room entity,
 * combining both the hotel identifier and room number. It is necessary
 * for properly identifying a specific room in a hotel.
 * </p>
 */
@Getter
@Setter
public class RoomId implements Serializable {

    /**
     * The hotel ID to which the room belongs.
     */
    private int hotel;

    /**
     * The unique room number within the specified hotel.
     */
    private int roomNr;

    /**
     * Checks whether this RoomId is equal to another RoomId.
     * <p>
     * Two RoomId instances are considered equal if their hotel ID and room number are the same.
     * </p>
     *
     * @param o the object to compare with
     * @return true if this RoomId is equal to the specified object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId that = (RoomId) o;
        return Objects.equals(getRoomNr(), that.getRoomNr()) && Objects.equals(getHotel(), that.getHotel());
    }

    /**
     * Returns a hash code value for the RoomId.
     * <p>
     * The hash code is generated based on the hotel ID and room number.
     * </p>
     *
     * @return the hash code value of this RoomId
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRoomNr(), getHotel());
    }
}
