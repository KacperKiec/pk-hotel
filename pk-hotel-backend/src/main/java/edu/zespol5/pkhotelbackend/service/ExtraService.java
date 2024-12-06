package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ExtraNotFoundException;
import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraRepository;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraSpecification;
import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.exception.ReservationNotFoundException;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Extra> getAllExtras(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void deleteExtra(Extra extra) {
        if(!repository.findExtraById(extra.getId()).isPresent()) {
            throw new ExtraNotFoundException("Extra with id " + extra.getId() + " not found");
        }
        repository.deleteById(extra.getId());
    }

    public Extra updateExtra(Extra extra) {
        var ext = repository.findExtraById(extra.getId()).orElseThrow(
                () -> new ExtraNotFoundException("Extra with id " + extra.getId() + " not found")
        );

        if(ext.getName() != null) {
            ext.setName(extra.getName());
        }

        if(ext.getPricePerDay() != null) {
            ext.setPricePerDay(extra.getPricePerDay());
        }

        return repository.save(ext);
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

    public List<Extra> getExtrasBy(
            String name,
            Double upperPriceLimit,
            Double lowerPriceLimit) {
        Specification<Extra> spec = Specification.where(ExtraSpecification.hasName(name))
                .and(ExtraSpecification.hasPriceGreaterOrEqualThan(lowerPriceLimit))
                .and(ExtraSpecification.hasPriceLessOrEqualThan(upperPriceLimit));
        return repository.findAll(spec);
    }
}
