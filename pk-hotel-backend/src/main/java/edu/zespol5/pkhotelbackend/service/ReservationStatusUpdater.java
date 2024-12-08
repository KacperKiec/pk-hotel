package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationStatus;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationStatusUpdater {

    private final ReservationRepository reservationRepository;

    public ReservationStatusUpdater(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateReservationStatuses() {
        LocalDate today = LocalDate.now();
        updateStatuses(today);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void updateStatusesOnStartup() {
        LocalDate today = LocalDate.now();
        updateStatuses(today);
    }

    private void updateStatuses(LocalDate today) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() != ReservationStatus.CANCELED) {
                if (!reservation.getCheckInDate().isBefore(today) && !reservation.getCheckOutDate().isAfter(today)) {
                    reservation.setStatus(ReservationStatus.IN_REALISATION);
                } else if (reservation.getCheckOutDate().isBefore(today)) {
                    reservation.setStatus(ReservationStatus.ENDED);
                }
                reservationRepository.save(reservation);
            }
        }
    }
}

