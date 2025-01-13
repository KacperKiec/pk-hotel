package edu.zespol5.pkhotelbackend.model.room_image;

import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key class for the `RoomImage` entity.
 * <p>
 * The `RoomImageId` class is used as a composite key to uniquely identify a `RoomImage` entity,
 * which represents the relationship between a room and an image in the hotel management system.
 * The key is composed of two parts: `RoomId` and `imageId`.
 * </p>
 */
@Getter
@Setter
@Embeddable
public class RoomImageId implements Serializable {

    /**
     * The ID of the room associated with the image.
     * This field represents the room to which the image belongs, and is part of the composite key.
     */
    private RoomId roomId;

    /**
     * The ID of the image associated with the room.
     * This field represents the image, and is part of the composite key.
     */
    private Integer imageId;

    /**
     * Compares this `RoomImageId` with another object for equality.
     * The comparison is based on the `roomId` and `imageId` fields.
     *
     * @param o The object to compare to.
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomImageId that = (RoomImageId) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(imageId, that.imageId);
    }

    /**
     * Returns a hash code value for this `RoomImageId`.
     * The hash code is computed based on the `roomId` and `imageId` fields.
     *
     * @return the hash code value for this `RoomImageId`.
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomId, imageId);
    }
}
