package edu.zespol5.pkhotelbackend.model.user;

/**
 * Enum representing the roles of a user within the system.
 * <p>
 * The `UserRole` enum defines the different roles a user can have within the system.
 * These roles determine the level of access and permissions the user has in the application.
 * </p>
 */
public enum UserRole {

    /**
     * Represents a regular client user.
     * Clients have standard access to the system's features, such as booking rooms, reviewing hotels, etc.
     */
    CLIENT,

    /**
     * Represents an administrative user.
     * Admins have higher-level access to manage and oversee system operations, such as managing hotels, rooms, and users.
     */
    ADMIN
}
