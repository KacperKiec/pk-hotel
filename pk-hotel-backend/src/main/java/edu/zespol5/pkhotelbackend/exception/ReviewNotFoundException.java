package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a review is not found in the system.
 * <p>
 * This exception is typically thrown when an attempt is made to access or retrieve a review that does
 * not exist in the system. It indicates that the requested review could not be found.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class ReviewNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code ReviewNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ReviewNotFoundException(String msg) {
        super(msg);
    }
}
