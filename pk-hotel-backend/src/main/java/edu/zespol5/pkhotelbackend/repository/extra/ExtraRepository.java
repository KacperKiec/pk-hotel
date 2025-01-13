package edu.zespol5.pkhotelbackend.repository.extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ExtraRepository} interface defines custom methods for managing
 * and querying {@link Extra} entities in the database. It includes methods for
 * saving, retrieving, and deleting extras, as well as custom queries for specific use cases.
 *
 * <p>
 * This interface provides flexibility to access and manipulate data related to extra services
 * offered in the hotel system, such as additional services or amenities that can be added
 * to a reservation.
 * </p>
 *
 * @see Extra
 */
public interface ExtraRepository {

    /**
     * Saves a given {@link Extra} entity to the database.
     *
     * @param extra the extra entity to save
     * @return the saved extra entity
     */
    Extra save(Extra extra);

    /**
     * Finds an {@link Extra} entity by its unique ID.
     *
     * @param id the ID of the extra
     * @return an {@link Optional} containing the found extra, or empty if not found
     */
    Optional<Extra> findExtraById(int id);

    /**
     * Finds an {@link Extra} entity by its name and price per day.
     *
     * @param name the name of the extra
     * @param pricePerDay the price of the extra per day
     * @return an {@link Optional} containing the found extra, or empty if not found
     */
    Optional<Extra> findExtraByNameAndPricePerDay(String name, Double pricePerDay);

    /**
     * Retrieves a paginated list of all {@link Extra} entities.
     *
     * @param pageable the pagination information
     * @return a {@link Page} containing the extra entities
     */
    Page<Extra> findAll(Pageable pageable);

    /**
     * Finds all {@link Extra} entities matching the provided specification.
     *
     * @param spec the specification to filter the extras
     * @return a {@link List} of extras that match the specification
     */
    List<Extra> findAll(Specification<Extra> spec);

    /**
     * Deletes an {@link Extra} entity by its ID.
     *
     * @param id the ID of the extra to delete
     */
    void deleteById(int id);

    /**
     * Finds all {@link Extra} entities associated with a specific reservation.
     *
     * @param id the ID of the reservation
     * @return a {@link List} of extras associated with the specified reservation
     */
    @Query("SELECT re.extra FROM ReservationExtra re WHERE re.reservation.id = :id")
    List<Extra> findExtrasByReservationId(@Param("id") int id);
}
