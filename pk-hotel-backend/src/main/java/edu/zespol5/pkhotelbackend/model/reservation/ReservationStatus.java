package edu.zespol5.pkhotelbackend.model.reservation;

/**
 * Enumeration representing the different statuses a reservation can have.
 * <p>
 * This enum is used to indicate the current state of a reservation. The possible states are:
 * <ul>
 *     <li><b>CANCELED</b> - The reservation has been canceled.</li>
 *     <li><b>ACCEPTED</b> - The reservation has been accepted by the hotel and is confirmed.</li>
 *     <li><b>IN_REALISATION</b> - The reservation is currently being processed or is in progress.</li>
 *     <li><b>ENDED</b> - The reservation has been completed and the client has checked out.</li>
 * </ul>
 * </p>
 */
public enum ReservationStatus {

    /**
     * Indicates that the reservation has been canceled.
     */
    CANCELED,

    /**
     * Indicates that the reservation has been accepted and confirmed by the hotel.
     */
    ACCEPTED,

    /**
     * Indicates that the reservation is currently in progress or being processed.
     */
    IN_REALISATION,

    /**
     * Indicates that the reservation has ended and the client has completed their stay.
     */
    ENDED
}
