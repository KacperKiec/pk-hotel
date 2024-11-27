package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ExtraNotFoundException;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraRepository;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraSpecification;
import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.exception.ReservationNotFoundException;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtraService {
    private final ExtraRepository repository;
    private final ReservationRepository reservationRepository;

    public ExtraService(ExtraRepository repository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    public Extra save(Extra extra) {
        return repository.save(extra);
    }

    public Extra getExtraById(int id) {
        return repository.findExtraById(id).orElseThrow(
                () -> new ExtraNotFoundException("Extra with id " + id + " was not found")
        );
    }

    public List<Extra> getExtrasByReservationId(int reservationId) {
        reservationRepository.findReservationById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException("Reservation with id " + reservationId + " was not found")
        );
        return repository.findExtrasByReservationId(reservationId);
    }

    public List<Extra> getAllExtras() {
        return repository.findAll();
    }

    public List<Extra> getExtrasBy(
            String name,
            double upperPriceLimit,
            double lowerPriceLimit) {
        Specification<Extra> spec = Specification.where(ExtraSpecification.hasName(name))
                .and(ExtraSpecification.hasPriceGreaterOrEqualThan(lowerPriceLimit))
                .and(ExtraSpecification.hasPriceLessOrEqualThan(upperPriceLimit));
        return repository.findAll(spec);
    }
}
