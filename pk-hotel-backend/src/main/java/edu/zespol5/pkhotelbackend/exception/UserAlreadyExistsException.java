package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a user already exists in the system.
 * <p>
 * This exception is typically thrown when an attempt is made to register a user who is already
 * present in the system, such as when an attempt is made to create a new account with an email address
 * or username that is already associated with an existing account.
 * </p>
 *
 * @see IllegalArgumentException
 */
public class UserAlreadyExistsException extends IllegalArgumentException {

    /**
     * Constructs a new {@code UserAlreadyExistsException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
