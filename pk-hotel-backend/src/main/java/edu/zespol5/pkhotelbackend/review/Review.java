package edu.zespol5.pkhotelbackend.review;

import edu.zespol5.pkhotelbackend.client.Client;
import edu.zespol5.pkhotelbackend.hotel.Hotel;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private int rating;
    private String content;
}
