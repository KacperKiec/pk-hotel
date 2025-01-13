package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when an attempt is made to create a resource that already exists in the system.
 * <p>
 * This exception is typically thrown when a user tries to create a resource (such as a user, hotel,
 * reservation, etc.) that already exists in the system. It indicates a conflict between the existing
 * resource and the new one being created.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class ResourceAlreadyExistsException extends IllegalArgumentException {

    /**
     * Constructs a new {@code ResourceAlreadyExistsException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}
