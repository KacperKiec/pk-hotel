package edu.zespol5.pkhotelbackend.model.hotel;

import edu.zespol5.pkhotelbackend.model.room.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String owner;
    private LocalDate registerDate;
    private String country;
    private String city;
    private String address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    public Hotel(){

    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public void removeRoom(Room room){
        rooms.remove(room);
    }
}
