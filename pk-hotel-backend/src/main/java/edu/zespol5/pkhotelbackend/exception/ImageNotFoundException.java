package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when an image is not found in the system.
 * <p>
 * This exception is typically used when a requested image is not found or is missing,
 * which can occur in scenarios such as an image request for a hotel, review, or other entities
 * where an image is expected but is not available.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class ImageNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code ImageNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
