package edu.zespol5.pkhotelbackend.model.room_convenience;

import edu.zespol5.pkhotelbackend.model.room.Room;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) used for requesting room conveniences.
 * <p>
 * The `RoomConvenienceRequestDTO` is used to transfer information about the room and the list of convenience IDs
 * that should be associated with the room. This object is typically used in service methods or controllers to
 * handle the request data for assigning conveniences to a room.
 * </p>
 */
@Data
public class RoomConvenienceRequestDTO {

    /**
     * The room to which conveniences will be assigned.
     * This represents the room in the hotel for which the conveniences are being requested.
     */
    private Room room;

    /**
     * A list of convenience IDs that should be associated with the specified room.
     * Each convenience ID represents a specific convenience (e.g., Wi-Fi, TV) that should be added to the room.
     */
    List<Integer> conveniencesIds;
}
