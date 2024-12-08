package edu.zespol5.pkhotelbackend.model.room_convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "room_convenience")
@Getter
@Setter
public class RoomConvenience {
    @EmbeddedId
    private RoomConvenienceId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumns({
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr"),
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    })
    private Room room;

    @ManyToOne
    @MapsId("convenienceId")
    @JoinColumn(name = "convenience_id")
    private Convenience convenience;

    public RoomConvenience() {

    }

    public RoomConvenience(Room room, Convenience convenience) {
        setRoom(room);
        setConvenience(convenience);
        RoomConvenienceId r = new RoomConvenienceId();
        RoomId roomId = new RoomId();
        roomId.setRoomNr(room.getRoomNr());
        roomId.setHotel(room.getHotel().getId());
        r.setRoomId(roomId);
        r.setConvenienceId(convenience.getId());
        setId(r);
    }
}
