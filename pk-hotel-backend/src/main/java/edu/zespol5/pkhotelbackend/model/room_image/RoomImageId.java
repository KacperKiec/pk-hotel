package edu.zespol5.pkhotelbackend.model.room_image;

import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RoomImageId implements Serializable {
    private RoomId roomId;
    private Integer imageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomImageId that = (RoomImageId) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, imageId);
    }
}