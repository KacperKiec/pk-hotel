package edu.zespol5.pkhotelbackend.model.room_image;

import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.model.room.Room;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for handling room image requests.
 * <p>
 * The `RoomImageRequestDTO` class is used to transfer data between layers in the application, specifically
 * when handling requests related to images of rooms. This DTO holds information about the room and
 * the associated images that should be added or managed.
 * </p>
 */
@Data
public class RoomImageRequestDTO {

    /**
     * The room to which the images are associated.
     * This field holds the `Room` entity that the images belong to.
     */
    private Room room;

    /**
     * The list of images associated with the specified room.
     * This field holds a list of `Image` entities that should be added to the specified room.
     */
    private List<Image> images;
}
