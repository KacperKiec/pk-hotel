package edu.zespol5.pkhotelbackend.model.room_image;

import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.model.room.Room;
import lombok.Data;

import java.util.List;

@Data
public class RoomImageRequestDTO {
    private Room room;
    private List<Image> images;
}
