package edu.zespol5.pkhotelbackend.model.reservation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {
    private String clientName;
    private String clientLastName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String hotelName;
    private int roomNr;
    private ReservationStatus status;
}
