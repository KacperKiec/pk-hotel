package edu.zespol5.pkhotelbackend.repository.image;

import edu.zespol5.pkhotelbackend.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ImageRepository} interface provides data access operations for managing
 * {@link Image} entities. It includes standard CRUD operations as well as custom queries
 * related to images associated with hotel rooms.
 *
 * <p>
 * This interface allows saving, retrieving, and deleting images. It also supports fetching
 * images associated with specific rooms in a hotel.
 * </p>
 *
 * @see Image
 */
public interface ImageRepository {

    /**
     * Saves a given {@link Image} entity to the database.
     *
     * @param image the image entity to save
     * @return the saved image entity
     */
    Image save(Image image);

    /**
     * Finds an {@link Image} entity by its unique ID.
     *
     * @param id the ID of the image
     * @return an {@link Optional} containing the found image, or empty if not found
     */
    Optional<Image> findImageById(int id);

    /**
     * Retrieves a list of all {@link Image} entities in the database.
     *
     * @return a {@link List} of all images
     */
    List<Image> findAll();

    /**
     * Deletes an {@link Image} entity by its ID.
     *
     * @param id the ID of the image to delete
     */
    void deleteById(int id);

    /**
     * Finds a list of {@link Image} entities associated with a specific room in a hotel.
     * This query fetches images based on the room number and the hotel ID.
     *
     * @param roomNr the room number to search for
     * @param hotelId the hotel ID to filter the rooms by
     * @return a {@link List} of images associated with the specified room in the specified hotel
     */
    @Query("SELECT ri.image FROM RoomImage ri WHERE ri.room.roomNr = :roomNr AND ri.room.hotel.id = :hotelId")
    List<Image> findImageByRoomId(@Param("roomNr") int roomNr, @Param("hotelId") int hotelId);
}
