package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a user cannot be found in the system.
 * <p>
 * This exception is typically thrown when an operation is attempted involving a user
 * that does not exist in the system, such as when trying to retrieve or modify a
 * non-existent user account.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class UserNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
