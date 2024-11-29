package edu.zespol5.pkhotelbackend.model.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private UserRole role;
}
