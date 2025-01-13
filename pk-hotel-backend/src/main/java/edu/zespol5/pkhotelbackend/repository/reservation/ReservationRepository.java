package edu.zespol5.pkhotelbackend.repository.reservation;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ReservationRepository} interface provides data access operations for the
 * {@link Reservation} entity. It supports standard CRUD operations, dynamic queries through
 * specifications, and pagination for querying reservation-related data.
 *
 * <p>
 * This interface allows saving, retrieving, updating, and deleting reservations.
 * It also supports querying reservations with pagination and filtering by dynamic criteria.
 * </p>
 *
 * @see Reservation
 */
public interface ReservationRepository {

    /**
     * Saves a given {@link Reservation} entity to the database.
     *
     * @param reservation the reservation entity to save
     * @return the saved reservation entity
     */
    Reservation save(Reservation reservation);

    /**
     * Finds a {@link Reservation} entity by its unique ID.
     *
     * @param id the ID of the reservation
     * @return an {@link Optional} containing the found reservation, or empty if not found
     */
    Optional<Reservation> findReservationById(int id);

    /**
     * Retrieves a list of all {@link Reservation} entities.
     *
     * @return a list of all reservations
     */
    List<Reservation> findAll();

    /**
     * Retrieves a paginated list of all {@link Reservation} entities.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of all reservations
     */
    Page<Reservation> findAll(Pageable pageable);

    /**
     * Retrieves a paginated list of all {@link Reservation} entities that match the given
     * dynamic filtering criteria.
     *
     * @param spec the specification defining dynamic filtering conditions
     * @param pageable the pagination information
     * @return a {@link Page} of reservations matching the given specification
     */
    Page<Reservation> findAll(Specification<Reservation> spec, Pageable pageable);

    /**
     * Deletes a {@link Reservation} entity by its unique ID.
     *
     * @param id the ID of the reservation to delete
     */
    void deleteById(int id);
}
