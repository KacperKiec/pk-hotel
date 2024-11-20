package edu.zespol5.pkhotelbackend.extra;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "extra")
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double pricePerDay;
}
