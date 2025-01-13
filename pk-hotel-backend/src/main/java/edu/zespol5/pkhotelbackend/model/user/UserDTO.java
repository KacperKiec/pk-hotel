package edu.zespol5.pkhotelbackend.model.user;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a user in the system.
 * <p>
 * The `UserDTO` class is used to transfer user data between layers (e.g., between the controller and the service layer).
 * This class contains a subset of the `User` entity fields and is used for sending user information over network requests.
 * </p>
 */
@Data
public class UserDTO {

    /**
     * Unique identifier for the user.
     * This is the user's ID that identifies them within the system.
     */
    private int id;

    /**
     * First name of the user.
     * This is the user's first name, which can be used in user-related functionalities.
     */
    private String firstName;

    /**
     * Last name of the user.
     * This is the user's last name, which can be used in user-related functionalities.
     */
    private String lastName;

    /**
     * Email address of the user.
     * The email address is used to identify the user in the system, usually for login or communication purposes.
     */
    private String email;

    /**
     * Birthdate of the user.
     * This represents the user's date of birth and may be used for calculating age or personalized services.
     */
    private LocalDate birthDate;

    /**
     * Role of the user in the system.
     * The role indicates whether the user is a `CLIENT` or `ADMIN`, determining the access and permissions granted.
     */
    private UserRole role;
}
