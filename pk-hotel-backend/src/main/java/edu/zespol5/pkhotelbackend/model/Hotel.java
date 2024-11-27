package edu.zespol5.pkhotelbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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

    public Hotel(){

    }
}
