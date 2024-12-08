package edu.zespol5.pkhotelbackend.model.room;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private int roomNr;
    private String hotelName;
    private int places;
    private double price;
    private RoomStandard standard;
    private String description;
    private List<Convenience> conveniences;
    private Double rating;
    private List<Image> images;
}
