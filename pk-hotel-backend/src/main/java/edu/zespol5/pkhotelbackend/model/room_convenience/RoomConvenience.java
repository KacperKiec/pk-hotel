package edu.zespol5.pkhotelbackend.model.room_convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing the relationship between a room and its conveniences.
 * <p>
 * The `RoomConvenience` class is used to associate a room with a specific convenience. This relationship
 * is represented by a composite primary key, and each instance of `RoomConvenience` connects a room
 * and a convenience, allowing a room to have multiple conveniences.
 * </p>
 */
@Entity
@Table(name = "room_convenience")
@Getter
@Setter
public class RoomConvenience {

    /**
     * The composite primary key for the RoomConvenience entity.
     * It uniquely identifies a room-convenience relationship.
     */
    @EmbeddedId
    private RoomConvenienceId id;

    /**
     * The room associated with the convenience.
     * This is a many-to-one relationship where multiple room-convenience
     * relationships can refer to the same room.
     */
    @ManyToOne
    @MapsId("roomId")
    @JoinColumns({
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr"),
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    })
    private Room room;

    /**
     * The convenience associated with the room.
     * This is a many-to-one relationship where multiple room-convenience
     * relationships can refer to the same convenience.
     */
    @ManyToOne
    @MapsId("convenienceId")
    @JoinColumn(name = "convenience_id")
    private Convenience convenience;

    /**
     * Default constructor.
     */
    public RoomConvenience() {
    }

    /**
     * Constructor that initializes a RoomConvenience instance by associating a room with a convenience.
     * It creates a composite key (RoomConvenienceId) based on the provided room and convenience.
     *
     * @param room       the room associated with the convenience
     * @param convenience the convenience associated with the room
     */
    public RoomConvenience(Room room, Convenience convenience) {
        setRoom(room);
        setConvenience(convenience);

        // Create a composite key based on the room and convenience details
        RoomConvenienceId r = new RoomConvenienceId();
        RoomId roomId = new RoomId();
        roomId.setRoomNr(room.getRoomNr());
        roomId.setHotel(room.getHotel().getId());
        r.setRoomId(roomId);
        r.setConvenienceId(convenience.getId());

        setId(r); // Set the composite key
    }
}
