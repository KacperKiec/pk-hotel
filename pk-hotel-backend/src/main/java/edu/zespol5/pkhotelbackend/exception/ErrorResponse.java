package edu.zespol5.pkhotelbackend.exception;

/**
 * Represents a generic error response containing an error code and a message.
 * <p>
 * This class is used to structure error responses sent to the client, allowing
 * for a consistent format of error reporting across the application.
 * </p>
 *
 * @param errorCode the unique code representing the error
 * @param message the detailed message describing the error
 */
public record ErrorResponse(String errorCode, String message) {
}
