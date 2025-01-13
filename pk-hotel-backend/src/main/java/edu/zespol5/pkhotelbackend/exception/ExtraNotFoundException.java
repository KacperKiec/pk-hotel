package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when an "extra" (e.g., additional service, item, or feature) is not found.
 * <p>
 * This exception is typically used when the application is unable to find a specific extra
 * resource requested by the user or system.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class ExtraNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code ExtraNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ExtraNotFoundException(String msg) {
        super(msg);
    }
}
