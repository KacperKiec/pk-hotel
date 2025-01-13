package edu.zespol5.pkhotelbackend.repository.convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ConvenienceRepository} interface defines custom methods for managing
 * and querying {@link Convenience} entities in the database. This interface includes
 * methods for saving, retrieving, and deleting conveniences, as well as custom
 * queries for specific use cases.
 *
 * <p>
 * It is intended to provide a flexible way to access and manipulate data related to
 * hotel room conveniences.
 * </p>
 *
 * @see Convenience
 */
public interface ConvenienceRepository {

    /**
     * Saves a given {@link Convenience} entity to the database.
     *
     * @param convenience the convenience entity to save
     * @return the saved convenience entity
     */
    Convenience save(Convenience convenience);

    /**
     * Finds a {@link Convenience} entity by its unique ID.
     *
     * @param id the ID of the convenience
     * @return an {@link Optional} containing the found convenience, or empty if not found
     */
    Optional<Convenience> findConvenienceById(int id);

    /**
     * Finds a {@link Convenience} entity by its name.
     *
     * @param name the name of the convenience
     * @return an {@link Optional} containing the found convenience, or empty if not found
     */
    Optional<Convenience> findConveniencesByName(String name);

    /**
     * Retrieves a paginated list of all {@link Convenience} entities.
     *
     * @param pageable the pagination information
     * @return a {@link Page} containing the convenience entities
     */
    Page<Convenience> findAll(Pageable pageable);

    /**
     * Deletes a {@link Convenience} entity by its ID.
     *
     * @param id the ID of the convenience to delete
     */
    void deleteById(int id);

    /**
     * Finds all conveniences associated with a specific room and hotel.
     *
     * @param roomNr  the room number
     * @param hotelId the ID of the hotel
     * @return a {@link List} of conveniences associated with the specified room and hotel
     */
    @Query("SELECT rc.convenience FROM RoomConvenience rc WHERE rc.room.roomNr = :roomNr AND rc.room.hotel.id = :hotelId")
    List<Convenience> findConvenienceByRoomId(@Param("roomNr") int roomNr, @Param("hotelId") int hotelId);
}
