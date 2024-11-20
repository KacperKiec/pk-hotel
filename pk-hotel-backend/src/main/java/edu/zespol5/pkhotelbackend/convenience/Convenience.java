package edu.zespol5.pkhotelbackend.convenience;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "convenience")
public class Convenience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
