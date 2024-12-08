package edu.zespol5.pkhotelbackend.model.room_convenience;

import edu.zespol5.pkhotelbackend.model.room.Room;
import lombok.Data;

import java.util.List;

@Data
public class RoomConvenienceRequestDTO {
    private Room room;
    List<Integer> conveniencesIds;
}
