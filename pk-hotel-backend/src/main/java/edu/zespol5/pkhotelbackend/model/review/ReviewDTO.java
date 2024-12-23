package edu.zespol5.pkhotelbackend.model.review;

import lombok.Data;

@Data
public class ReviewDTO {
    private int id;
    private String hotelName;
    private String userFirstName;
    private String userLastName;
    private int rating;
    private String comment;
}
