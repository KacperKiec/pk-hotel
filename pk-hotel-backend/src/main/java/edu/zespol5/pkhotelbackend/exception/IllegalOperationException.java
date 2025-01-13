package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when an illegal operation is attempted.
 * <p>
 * This exception is typically used when an operation is attempted in an illegal or invalid state
 * within the application. It extends {@link IllegalStateException} to indicate that the exception
 * occurs due to an inappropriate state in which the operation cannot be performed.
 * </p>
 *
 * @see IllegalStateException
 */
public class IllegalOperationException extends IllegalStateException {

    /**
     * Constructs a new {@code IllegalOperationException} with the specified detail message.
     *
     * @param msg the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public IllegalOperationException(String msg) {
        super(msg);
    }
}
