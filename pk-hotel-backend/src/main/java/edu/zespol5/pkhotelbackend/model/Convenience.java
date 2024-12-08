package edu.zespol5.pkhotelbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "convenience")
public class Convenience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
