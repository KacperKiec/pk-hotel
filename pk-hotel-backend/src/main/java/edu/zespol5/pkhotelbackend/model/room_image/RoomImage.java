package edu.zespol5.pkhotelbackend.model.room_image;

import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "room_image")
@Getter
@Setter
public class RoomImage {
    @EmbeddedId
    private RoomImageId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumns({
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr"),
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    })
    private Room room;

    @ManyToOne
    @MapsId("imageId")
    @JoinColumn(name = "image_id")
    private Image image;

    public RoomImage(){

    }

    public RoomImage(Room room, Image image){
        setRoom(room);
        setImage(image);
        RoomId roomId = new RoomId();
        roomId.setHotel(roomId.getHotel());
        roomId.setRoomNr(room.getRoomNr());
        RoomImageId id = new RoomImageId();
        id.setRoomId(roomId);
        id.setImageId(id.getImageId());
        setId(id);
    }
}
