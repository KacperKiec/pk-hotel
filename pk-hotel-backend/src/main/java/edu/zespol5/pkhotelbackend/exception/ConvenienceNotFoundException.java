package edu.zespol5.pkhotelbackend.exception;

/**
 * Exception thrown when a requested convenience (e.g., service, feature) is not found.
 * <p>
 * This exception extends {@link IllegalArgumentException} and is used to indicate
 * that a specific convenience was not found, usually when querying or interacting with
 * a hotel service or resource.
 * </p>
 */
public class ConvenienceNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a new ConvenienceNotFoundException with the specified detail message.
     *
     * @param msg the detail message which explains the cause of the exception
     */
    public ConvenienceNotFoundException(String msg) {
        super(msg);
    }
}
