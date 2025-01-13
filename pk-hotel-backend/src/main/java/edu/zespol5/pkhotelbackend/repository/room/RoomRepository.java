package edu.zespol5.pkhotelbackend.repository.room;

import edu.zespol5.pkhotelbackend.model.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * The {@code RoomRepository} interface provides methods for managing and querying
 * {@link Room} entities. It includes basic CRUD operations, custom queries for room retrieval,
 * and dynamic filtering through specifications.
 *
 * <p>
 * This interface defines methods for saving, finding, and deleting rooms. It supports pagination
 * and filtering based on various criteria via {@link Specification}.
 * </p>
 *
 * @see Room
 * @see Specification
 */
public interface RoomRepository {

    /**
     * Saves the given {@link Room} entity to the database.
     *
     * @param room the room entity to save
     * @return the saved room entity
     */
    Room save(Room room);

    /**
     * Finds a room by its hotel ID and room number.
     *
     * @param hotelId the ID of the hotel to search within
     * @param roomNr the room number to search for
     * @return an {@link Optional} containing the room if found, or an empty Optional if not
     */
    Optional<Room> findRoomByHotel_IdAndRoomNr(int hotelId, int roomNr);

    /**
     * Retrieves a paginated list of rooms.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of rooms
     */
    Page<Room> findAll(Pageable pageable);

    /**
     * Retrieves a paginated list of rooms based on the given {@link Specification}.
     *
     * @param spec the specification used for filtering rooms
     * @param pageable the pagination information
     * @return a {@link Page} of rooms matching the specification
     */
    Page<Room> findAll(Specification<Room> spec, Pageable pageable);

    /**
     * Retrieves a list of rooms based on the given {@link Specification}.
     *
     * @param spec the specification used for filtering rooms
     * @return a list of rooms matching the specification
     */
    List<Room> findAll(Specification<Room> spec);

    /**
     * Deletes a room by its room number and hotel ID.
     *
     * @param roomNr the room number of the room to delete
     * @param hotelId the hotel ID of the room to delete
     */
    void deleteRoomByRoomNrAndHotelId(Integer roomNr, Integer hotelId);
}
