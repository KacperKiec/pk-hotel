package edu.zespol5.pkhotelbackend.model.connectors;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.room.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "room_convenience")
@Setter
@Getter
public class RoomConvenience {
    @EmbeddedId
    private RoomConvenienceId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "room_nr", referencedColumnName = "room_nr"),
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    })
    private Room room;

    @ManyToOne
    @JoinColumn(name = "convenience_id")
    private Convenience convenience;
}
