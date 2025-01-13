package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ExtraNotFoundException;
import edu.zespol5.pkhotelbackend.exception.ResourceAlreadyExistsException;
import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraRepository;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraSpecification;
import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.exception.ReservationNotFoundException;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code ExtraService} provides business logic for managing extras related to reservations.
 * It handles CRUD operations and ensures constraints are enforced, such as preventing duplicate extras.
 * The service interacts with {@link ExtraRepository} and {@link ReservationRepository} for accessing and modifying extra and reservation data.
 *
 * <p>
 * The service includes methods to save, update, delete, and fetch extras by their IDs or reservation IDs.
 * It also provides a search functionality to retrieve extras based on specific filters like name and price.
 * If an extra or reservation is not found, an appropriate exception is thrown.
 * </p>
 *
 * @see Extra
 * @see ExtraRepository
 * @see ReservationRepository
 * @see ExtraNotFoundException
 * @see ResourceAlreadyExistsException
 * @see ReservationNotFoundException
 */
@Service
public class ExtraService {

    private final ExtraRepository repository;
    private final ReservationRepository reservationRepository;

    /**
     * Constructs a new instance of {@code ExtraService} with the provided repositories.
     *
     * @param repository the {@link ExtraRepository} to be used by this service.
     * @param reservationRepository the {@link ReservationRepository} for handling reservations.
     */
    public ExtraService(ExtraRepository repository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Saves a new extra to the database if an extra with the same name and price does not already exist.
     *
     * @param extra the {@link Extra} entity to be saved.
     * @return the saved extra entity.
     * @throws ResourceAlreadyExistsException if an extra with the same name and price already exists.
     */
    public Extra save(Extra extra) {
        if (repository.findExtraByNameAndPricePerDay(extra.getName(), extra.getPricePerDay()).isPresent()) {
            throw new ResourceAlreadyExistsException("Extra with name " + extra.getName() + " and price " + extra.getPricePerDay() + " already exists. Choose one from existing extras");
        }
        return repository.save(extra);
    }

    /**
     * Retrieves all extras in a paginated manner.
     *
     * @param pageable the pagination information.
     * @return a page of extras.
     */
    public Page<Extra> getAllExtras(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Deletes the specified extra from the database.
     *
     * @param extra the extra to be deleted.
     * @throws ExtraNotFoundException if the specified extra does not exist in the database.
     */
    @Transactional
    public void deleteExtra(Extra extra) {
        repository.findExtraById(extra.getId()).orElseThrow(
                () -> new ExtraNotFoundException("Extra with id " + extra.getId() + " not found")
        );

        repository.deleteById(extra.getId());
    }

    /**
     * Updates an existing extra in the database.
     *
     * @param extra the extra with updated data.
     * @return the updated extra entity.
     * @throws ExtraNotFoundException if the specified extra does not exist in the database.
     */
    public Extra updateExtra(Extra extra) {
        var ext = repository.findExtraById(extra.getId()).orElseThrow(
                () -> new ExtraNotFoundException("Extra with id " + extra.getId() + " not found")
        );

        if (ext.getName() != null) {
            ext.setName(extra.getName());
        }

        if (ext.getPricePerDay() != null) {
            ext.setPricePerDay(extra.getPricePerDay());
        }

        return repository.save(ext);
    }

    /**
     * Retrieves an extra by its ID.
     *
     * @param id the ID of the extra to retrieve.
     * @return the found extra entity.
     * @throws ExtraNotFoundException if the extra with the specified ID is not found.
     */
    public Extra getExtraById(int id) {
        return repository.findExtraById(id).orElseThrow(
                () -> new ExtraNotFoundException("Extra with id " + id + " was not found")
        );
    }

    /**
     * Retrieves all extras associated with a given reservation ID.
     *
     * @param reservationId the ID of the reservation to retrieve extras for.
     * @return a list of extras associated with the given reservation.
     * @throws ReservationNotFoundException if the reservation with the specified ID is not found.
     */
    public List<Extra> getExtrasByReservationId(int reservationId) {
        reservationRepository.findReservationById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException("Reservation with id " + reservationId + " was not found")
        );
        return repository.findExtrasByReservationId(reservationId);
    }

    /**
     * Retrieves a list of extras based on the specified filters.
     *
     * @param name the name of the extra to search for.
     * @param upperPriceLimit the maximum price per day for the extra.
     * @param lowerPriceLimit the minimum price per day for the extra.
     * @return a list of extras matching the specified criteria.
     */
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
