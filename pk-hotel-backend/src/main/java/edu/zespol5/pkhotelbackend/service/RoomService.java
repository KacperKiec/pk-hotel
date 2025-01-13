package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.*;
import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.model.room_convenience.RoomConvenience;
import edu.zespol5.pkhotelbackend.model.room.RoomDTO;
import edu.zespol5.pkhotelbackend.model.room_image.RoomImage;
import edu.zespol5.pkhotelbackend.repository.convenience.ConvenienceRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomStandard;
import edu.zespol5.pkhotelbackend.repository.image.ImageRepository;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code RoomService} is a service class that handles operations related to hotel rooms.
 * It includes functionalities for adding, updating, deleting rooms, adding and removing conveniences, managing images,
 * and retrieving room data with various filters.
 *
 * <p>
 * This service allows:
 * <ul>
 *   <li>Saving a new room to a hotel.</li>
 *   <li>Adding or removing conveniences to/from a room.</li>
 *   <li>Adding or removing images associated with a room.</li>
 *   <li>Retrieving a room by ID and room number.</li>
 *   <li>Getting all rooms or filtered rooms with pagination support.</li>
 *   <li>Updating room details.</li>
 *   <li>Deleting a room from the hotel.</li>
 * </ul>
 * </p>
 *
 * @see Room
 * @see RoomDTO
 * @see RoomConvenience
 * @see RoomImage
 * @see HotelRepository
 * @see ConvenienceRepository
 * @see ReviewRepository
 * @see ImageRepository
 * @see RoomRepository
 */
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ConvenienceRepository convenienceRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    /**
     * Constructs a new {@code RoomService} with the provided repositories.
     *
     * @param roomRepository the repository used to access room data.
     * @param hotelRepository the repository used to access hotel data.
     * @param convenienceRepository the repository used to access room convenience data.
     * @param reviewRepository the repository used to access review data.
     * @param imageRepository the repository used to access image data.
     */
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, ConvenienceRepository convenienceRepository, ReviewRepository reviewRepository, ImageRepository imageRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.convenienceRepository = convenienceRepository;
        this.reviewRepository = reviewRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Saves a new room for a hotel.
     * If the room already exists for the hotel, an exception will be thrown.
     *
     * @param room the room to save.
     * @return a {@link RoomDTO} containing the details of the saved room.
     * @throws HotelNotFoundException if the hotel is not found.
     * @throws ResourceAlreadyExistsException if the room already exists for the specified hotel.
     */
    public RoomDTO saveRoom(Room room) {
        var hotel = hotelRepository.findHotelById(room.getHotel().getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );

        if (roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).isPresent()) {
            throw new ResourceAlreadyExistsException("Room for hotel with id " + room.getHotel().getId() + " and room number " + room.getRoomNr() + " already exists");
        }
        var result = roomRepository.save(room);

        hotel.addRoom(result);
        hotelRepository.save(hotel);

        return toDTO(result);
    }

    /**
     * Adds a list of conveniences to a room.
     * Each convenience is validated by ID before being added to the room.
     *
     * @param room the room to add conveniences to.
     * @param convenienceIds the IDs of the conveniences to add.
     * @return a {@link RoomDTO} containing the updated room details.
     * @throws RoomNotFoundException if the room is not found.
     * @throws ConvenienceNotFoundException if any of the conveniences are not found.
     */
    @Transactional
    public RoomDTO addConveniences(Room room, List<Integer> convenienceIds) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        for (Integer convenienceId : convenienceIds) {
            var conv = convenienceRepository.findConvenienceById(convenienceId).orElseThrow(
                    () -> new ConvenienceNotFoundException("Convenience not found")
            );

            RoomConvenience roomConvenience = new RoomConvenience(room, conv);

            if (!existingRoom.getConveniences().contains(roomConvenience)) {
                existingRoom.addConvenience(roomConvenience);
            }
        }

        return toDTO(roomRepository.save(existingRoom));
    }

    /**
     * Removes a list of conveniences from a room.
     * Each convenience is identified by its ID and removed from the room if it exists.
     *
     * @param room the room to remove conveniences from.
     * @param convenienceIds the IDs of the conveniences to remove.
     * @throws RoomNotFoundException if the room is not found.
     * @throws ConvenienceNotFoundException if any of the conveniences are not found.
     */
    @Transactional
    public void removeConvenience(Room room, List<Integer> convenienceIds) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        for (Integer id : convenienceIds) {
            var convenience = existingRoom.getConveniences().stream()
                    .filter(conv -> conv.getConvenience().getId().equals(id))
                    .findFirst()
                    .orElseThrow(
                            () -> new ConvenienceNotFoundException("Convenience not found")
                    );

            existingRoom.removeConvenience(convenience);
        }
        roomRepository.save(existingRoom);
    }

    /**
     * Adds a list of images to a room.
     * Each image is validated and added to the room's image collection.
     *
     * @param room the room to add images to.
     * @param images the images to add.
     * @return a {@link RoomDTO} containing the updated room details.
     * @throws RoomNotFoundException if the room is not found.
     * @throws ImageNotFoundException if any of the images are not found.
     */
    @Transactional
    public RoomDTO addImages(Room room, List<Image> images) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        for (Image img : images) {
            if (img.getPath() != null && !img.getPath().isEmpty()) {
                var temp = imageRepository.save(img);
                RoomImage roomImage = new RoomImage(room, temp);
                existingRoom.addImage(roomImage);
            }
        }

        return toDTO(roomRepository.save(existingRoom));
    }

    /**
     * Removes a list of images from a room.
     * Each image is identified by its ID and removed from the room's image collection.
     *
     * @param room the room to remove images from.
     * @param imagesIds the IDs of the images to remove.
     * @throws RoomNotFoundException if the room is not found.
     * @throws ImageNotFoundException if any of the images are not found.
     */
    @Transactional
    public void removeImage(Room room, List<Integer> imagesIds) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        for (Integer id : imagesIds) {
            var img = existingRoom.getImages().stream()
                    .filter(im -> im.getImage().getId().equals(id))
                    .findFirst().orElseThrow(
                            () -> new ImageNotFoundException("Image not found")
                    );

            existingRoom.removeImage(img);
        }
        roomRepository.save(existingRoom);
    }

    /**
     * Retrieves a room by hotel ID and room number.
     *
     * @param hotelId the ID of the hotel the room belongs to.
     * @param roomNr the room number.
     * @return a {@link RoomDTO} containing the room details.
     * @throws HotelNotFoundException if the hotel is not found.
     * @throws RoomNotFoundException if the room is not found.
     */
    public RoomDTO getRoomById(int hotelId, int roomNr) {
        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );
        return toDTO(roomRepository.findRoomByHotel_IdAndRoomNr(hotelId, roomNr).orElseThrow(
                () -> new RoomNotFoundException("Room with id " + hotelId + "/" + roomNr + " was not found")
        ));
    }

    /**
     * Retrieves all rooms with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link RoomDTO} objects containing the room details.
     */
    public Page<RoomDTO> getAllRooms(Pageable pageable){
        return roomRepository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Filters rooms based on various criteria such as hotel ID, room number, price range, room standard, available dates, etc.
     * Supports pagination.
     *
     * @param hotelId the ID of the hotel the room belongs to.
     * @param roomNr the room number.
     * @param lowerPriceLimit the lower price limit.
     * @param upperPriceLimit the upper price limit.
     * @param standard the room standard.
     * @param places the number of available places in the room.
     * @param startDate the start date for room availability.
     * @param endDate the end date for room availability.
     * @param cities the list of cities to filter rooms by.
     * @param countries the list of countries to filter rooms by.
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link RoomDTO} objects containing the room details.
     */
    public Page<RoomDTO> getRoomsBy(
            Integer hotelId,
            Integer roomNr,
            Double lowerPriceLimit,
            Double upperPriceLimit,
            RoomStandard standard,
            Integer places,
            LocalDate startDate,
            LocalDate endDate,
            List<String> cities,
            List<String> countries,
            Pageable pageable) {
        if(hotelId != null) {
            hotelRepository.findHotelById(hotelId).orElseThrow(
                    () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
            );
        }

        Specification<Room> spec = Specification.where(RoomSpecification.hasHotelId(hotelId))
                .and(RoomSpecification.hasRoomNr(roomNr))
                .and(RoomSpecification.hasPlaces(places))
                .and(RoomSpecification.hasPriceLessOrEqualThan(upperPriceLimit))
                .and(RoomSpecification.hasPriceGreaterOrEqualThan(lowerPriceLimit))
                .and(RoomSpecification.hasStandard(standard))
                .and(RoomSpecification.hasCity(cities))
                .and(RoomSpecification.hasCountry(countries))
                .and(RoomSpecification.isAvailable(startDate, endDate));
        return roomRepository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * Updates an existing room's details such as the number of places, price, standard, and description.
     *
     * @param room the room to update.
     * @return a {@link RoomDTO} containing the updated room details.
     * @throws RoomNotFoundException if the room is not found.
     */
    public RoomDTO updateRoom(Room room) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(
                room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        if (room.getPlaces() > 0) {
            existingRoom.setPlaces(room.getPlaces());
        }
        if (room.getPrice() > 0) {
            existingRoom.setPrice(room.getPrice());
        }
        if (room.getStandard() != null) {
            existingRoom.setStandard(room.getStandard());
        }
        if (room.getDescription() != null) {
            existingRoom.setDescription(room.getDescription());
        }

        return toDTO(roomRepository.save(existingRoom));
    }

    /**
     * Deletes a room from a hotel by its room number and hotel ID.
     *
     * @param room the room to delete.
     * @throws RoomNotFoundException if the room is not found.
     * @throws HotelNotFoundException if the hotel is not found.
     */
    @Transactional
    public void deleteRoom(Room room) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(
                room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        var existingHotel = hotelRepository.findHotelById(room.getHotel().getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );

        existingHotel.removeRoom(existingRoom);

        roomRepository.deleteRoomByRoomNrAndHotelId(room.getRoomNr(), room.getHotel().getId());
    }

    /**
     * Converts a {@link Room} to a {@link RoomDTO}.
     *
     * @param room the room to convert.
     * @return a {@link RoomDTO} containing the room's details.
     */
    private RoomDTO toDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomNr(room.getRoomNr());
        dto.setHotelName(room.getHotel().getName());
        dto.setPlaces(room.getPlaces());
        dto.setPrice(room.getPrice());
        dto.setStandard(room.getStandard());
        dto.setDescription(room.getDescription());
        dto.setConveniences(convenienceRepository.findConvenienceByRoomId(room.getRoomNr(), room.getHotel().getId()));

        Double rating = reviewRepository.findAverageRatingByHotelId(room.getHotel().getId());

        dto.setRating(rating != null ? rating : 0.0);
        dto.setImages(imageRepository.findImageByRoomId(room.getRoomNr(), room.getHotel().getId()));

        return dto;
    }
}
