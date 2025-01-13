package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a room is not found in the system.
 * <p>
 * This exception is typically thrown when an attempt is made to access or retrieve a room that does
 * not exist in the system. It indicates that the requested room could not be found.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class RoomNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code RoomNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public RoomNotFoundException(String msg) {
        super(msg);
    }
}
