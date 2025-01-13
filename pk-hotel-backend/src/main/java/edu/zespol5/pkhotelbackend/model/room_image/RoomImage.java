package edu.zespol5.pkhotelbackend.model.room_image;

import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing an image associated with a room in a hotel.
 * <p>
 * The `RoomImage` class is used to represent a relationship between a room and an image in the context of the hotel management system.
 * Each room can have multiple images, and each image is associated with a specific room in a hotel.
 * </p>
 */
@Entity
@Table(name = "room_image")
@Getter
@Setter
public class RoomImage {

    /**
     * The composite primary key for the `RoomImage` entity, representing the relationship between a room and an image.
     */
    @EmbeddedId
    private RoomImageId id;

    /**
     * The room to which the image is associated.
     * This is a many-to-one relationship between the `RoomImage` entity and the `Room` entity.
     */
    @ManyToOne
    @MapsId("roomId")
    @JoinColumns({
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr"),
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    })
    private Room room;

    /**
     * The image that is associated with the room.
     * This is a many-to-one relationship between the `RoomImage` entity and the `Image` entity.
     */
    @ManyToOne
    @MapsId("imageId")
    @JoinColumn(name = "image_id")
    private Image image;

    /**
     * Default constructor.
     * Creates an instance of the `RoomImage` entity without initializing the room and image associations.
     */
    public RoomImage() {
    }

    /**
     * Constructor to create a new `RoomImage` with a room and an image.
     * This constructor initializes the relationships between the `Room`, `Image`, and `RoomImageId`.
     *
     * @param room  The room associated with the image.
     * @param image The image associated with the room.
     */
    public RoomImage(Room room, Image image) {
        setRoom(room);
        setImage(image);
        RoomId roomId = new RoomId();
        roomId.setHotel(room.getHotel().getId());
        roomId.setRoomNr(room.getRoomNr());
        RoomImageId id = new RoomImageId();
        id.setRoomId(roomId);
        id.setImageId(image.getId());
        setId(id);
    }
}
