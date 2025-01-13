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

/**
 * {@code ReservationStatusUpdater} is a component that automatically updates the status of hotel reservations
 * based on the current date. The status of a reservation is updated based on its check-in and check-out dates:
 * - If the reservation's check-in date is today, the status is set to "IN_REALISATION".
 * - If the reservation's check-out date is in the past, the status is set to "ENDED".
 * - Reservations that are canceled are not updated.
 *
 * <p>
 * This service includes:
 * <ul>
 *   <li>Scheduled task to update reservation statuses every day at midnight.</li>
 *   <li>Event listener to update statuses when the application starts.</li>
 * </ul>
 * </p>
 *
 * @see Reservation
 * @see ReservationStatus
 * @see ReservationRepository
 */
@Component
public class ReservationStatusUpdater {

    private final ReservationRepository reservationRepository;

    /**
     * Constructs a new {@code ReservationStatusUpdater} with the provided {@link ReservationRepository}.
     *
     * @param reservationRepository the {@link ReservationRepository} used to access reservation data.
     */
    public ReservationStatusUpdater(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Updates the status of reservations every day at midnight.
     * The statuses are updated based on the current date:
     * <ul>
     *   <li>If the check-in date is today, the status is set to "IN_REALISATION".</li>
     *   <li>If the check-out date is in the past, the status is set to "ENDED".</li>
     * </ul>
     * This method is triggered by a scheduled task defined by the cron expression.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateReservationStatuses() {
        LocalDate today = LocalDate.now();
        updateStatuses(today);
    }

    /**
     * Updates the status of reservations when the application starts.
     * This method is triggered by the {@link ApplicationReadyEvent}.
     *
     * @see ApplicationReadyEvent
     */
    @EventListener(ApplicationReadyEvent.class)
    public void updateStatusesOnStartup() {
        LocalDate today = LocalDate.now();
        updateStatuses(today);
    }

    /**
     * Helper method to update the status of reservations based on the current date.
     *
     * @param today the current date used to update reservation statuses.
     */
    private void updateStatuses(LocalDate today) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            // Skip canceled reservations
            if (reservation.getStatus() != ReservationStatus.CANCELED) {
                // If check-in date is today, set the status to IN_REALISATION
                if (!reservation.getCheckInDate().isBefore(today) && !reservation.getCheckOutDate().isAfter(today)) {
                    reservation.setStatus(ReservationStatus.IN_REALISATION);
                }
                // If check-out date is before today, set the status to ENDED
                else if (reservation.getCheckOutDate().isBefore(today)) {
                    reservation.setStatus(ReservationStatus.ENDED);
                }
                reservationRepository.save(reservation);
            }
        }
    }
}
