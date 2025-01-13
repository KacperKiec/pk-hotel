package edu.zespol5.pkhotelbackend.model.room_convenience;

import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Embeddable class that represents the composite primary key for the RoomConvenience entity.
 * <p>
 * The `RoomConvenienceId` class is used to uniquely identify a room-convenience association by combining
 * the room identifier (`roomId`) and the convenience identifier (`convenienceId`).
 * This composite key is embedded in the `RoomConvenience` entity to represent the relationship.
 * </p>
 */
@Setter
@Getter
@Embeddable
public class RoomConvenienceId implements Serializable {

    /**
     * The unique identifier of the room in the hotel.
     * This is a part of the composite key used to identify the room-convenience relationship.
     */
    private RoomId roomId;

    /**
     * The unique identifier of the convenience in the hotel.
     * This is a part of the composite key used to identify the room-convenience relationship.
     */
    private Integer convenienceId;

    /**
     * Compares this RoomConvenienceId with another object for equality.
     * The comparison is based on the `roomId` and `convenienceId` fields.
     *
     * @param o the object to compare with
     * @return true if this RoomConvenienceId is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomConvenienceId that = (RoomConvenienceId) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(convenienceId, that.convenienceId);
    }

    /**
     * Computes the hash code for this RoomConvenienceId.
     * The hash code is generated based on the `roomId` and `convenienceId` fields.
     *
     * @return the hash code for this RoomConvenienceId
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomId, convenienceId);
    }
}
