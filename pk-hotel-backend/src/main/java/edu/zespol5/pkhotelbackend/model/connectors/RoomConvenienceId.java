package edu.zespol5.pkhotelbackend.model.connectors;

import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class RoomConvenienceId implements Serializable {
    private RoomId roomId;
    private Integer convenienceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomConvenienceId that = (RoomConvenienceId) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(convenienceId, that.convenienceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, convenienceId);
    }
}
