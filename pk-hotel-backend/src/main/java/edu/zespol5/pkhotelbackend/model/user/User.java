package edu.zespol5.pkhotelbackend.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
