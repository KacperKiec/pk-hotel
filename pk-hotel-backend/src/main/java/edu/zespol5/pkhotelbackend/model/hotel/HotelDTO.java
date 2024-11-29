package edu.zespol5.pkhotelbackend.model.hotel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelDTO {
    private int id;
    private String name;
    private String owner;
    private LocalDate registerDate;
    private String country;
    private String city;
    private String address;
}
