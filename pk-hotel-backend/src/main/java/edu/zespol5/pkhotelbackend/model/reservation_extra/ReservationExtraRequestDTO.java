package edu.zespol5.pkhotelbackend.model.reservation_extra;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class ReservationExtraRequestDTO {
    private Reservation reservation;
    private List<Integer> extraIds;
}
