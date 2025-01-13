package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a reservation is not found in the system.
 * <p>
 * This exception is typically thrown when a user tries to access or modify a reservation
 * that does not exist in the system. It can be triggered when attempting to retrieve a reservation
 * by its ID or other identifier and no matching record is found.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class ReservationNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code ReservationNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ReservationNotFoundException(String msg) {
        super(msg);
    }
}
