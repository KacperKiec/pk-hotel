package edu.zespol5.pkhotelbackend.controller;

import edu.zespol5.pkhotelbackend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling custom exceptions across all controllers.
 * <p>
 * This class catches specific exceptions and returns appropriate HTTP responses with error details.
 * It centralizes error handling and ensures consistent error responses for the entire application.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ConvenienceNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(ConvenienceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConvenienceNotFoundException(ConvenienceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("810", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link ExtraNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(ExtraNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExtraNotFoundException(ExtraNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("811", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link HotelNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHotelNotFoundException(HotelNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("812", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link ReservationNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("813", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link ReviewNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("814", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link RoomNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomNotFoundException(RoomNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("815", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link UserNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("816", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link UserAlreadyExistsException} by returning an HTTP 409 (Conflict) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("817", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link IllegalOperationException} by returning an HTTP 400 (Bad Request) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorResponse> handleIllegalOperationException(IllegalOperationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("819", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ImageNotFoundException} by returning an HTTP 404 (Not Found) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("820", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link ResourceAlreadyExistsException} by returning an HTTP 409 (Conflict) status with an error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error details and the HTTP status code
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("821", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
