package edu.zespol5.pkhotelbackend.repository.hotel;

import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code HotelRepository} interface defines custom methods for managing and
 * querying {@link Hotel} entities in the database. This interface provides functionality
 * to save, retrieve, and delete hotel records, as well as support for dynamic queries.
 *
 * @see Hotel
 */
public interface HotelRepository {

    /**
     * Saves a given {@link Hotel} entity to the database.
     *
     * @param hotel the hotel entity to save
     * @return the saved hotel entity
     */
    Hotel save(Hotel hotel);

    /**
     * Finds a {@link Hotel} entity by its unique ID.
     *
     * @param id the ID of the hotel
     * @return an {@link Optional} containing the found hotel, or empty if not found
     */
    Optional<Hotel> findHotelById(int id);

    /**
     * Retrieves a list of all {@link Hotel} entities in the database.
     *
     * @return a {@link List} of all hotels
     */
    List<Hotel> findAll();

    /**
     * Retrieves a paginated and dynamically filtered list of {@link Hotel} entities.
     *
     * @param spec the specification to filter the hotels
     * @param pageable the pagination information
     * @return a {@link Page} containing the filtered and paginated list of hotels
     */
    Page<Hotel> findAll(Specification<Hotel> spec, Pageable pageable);

    /**
     * Deletes a {@link Hotel} entity by its ID.
     *
     * @param id the ID of the hotel to delete
     */
    void deleteById(int id);
}
