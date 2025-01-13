package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a hotel is not found.
 * <p>
 * This exception is typically used when the system or application is unable to find
 * a specific hotel resource based on the provided identifiers or criteria.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class HotelNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code HotelNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public HotelNotFoundException(String msg) {
        super(msg);
    }
}
