package edu.zespol5.pkhotelbackend.model.reservation;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the details of a reservation.
 * <p>
 * This class is used for transferring reservation data between different layers of the application.
 * It contains simplified information about a reservation, such as client details, room information,
 * reservation dates, and the total price.
 * </p>
 */
@Data
public class ReservationDTO {

    /**
     * The unique identifier of the reservation.
     */
    private int id;

    /**
     * The first name of the client who made the reservation.
     */
    private String clientName;

    /**
     * The last name of the client who made the reservation.
     */
    private String clientLastName;

    /**
     * The check-in date for the reservation.
     */
    private LocalDate checkInDate;

    /**
     * The check-out date for the reservation.
     */
    private LocalDate checkOutDate;

    /**
     * The name of the hotel where the reservation was made.
     */
    private String hotelName;

    /**
     * The room number reserved for the client.
     */
    private int roomNr;

    /**
     * The status of the reservation (CANCELED, ACCEPTED, IN_REALISATION, ENDED).
     * <p>
     * This field is an enumeration that represents the current status of the reservation.
     * </p>
     */
    private ReservationStatus status;

    /**
     * The total price for the reservation, including the cost of the room and any additional extras.
     */
    private Double totalPrice;
}
