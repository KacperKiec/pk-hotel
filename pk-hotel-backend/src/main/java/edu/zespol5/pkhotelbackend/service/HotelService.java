package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.model.hotel.HotelDTO;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelSpecification;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code HotelService} provides business logic for managing hotels in the system.
 * It handles the CRUD operations and includes methods for searching hotels with filters.
 * The service interacts with {@link HotelRepository}, {@link RoomRepository}, and {@link ReviewRepository}
 * for accessing hotel, room, and review data, respectively.
 *
 * <p>
 * The service includes methods to create, update, delete, and fetch hotel information, as well as fetch
 * filtered hotels based on various search criteria. If a hotel is not found when attempting an operation,
 * a {@link HotelNotFoundException} is thrown.
 * </p>
 *
 * @see Hotel
 * @see HotelDTO
 * @see HotelRepository
 * @see RoomRepository
 * @see ReviewRepository
 * @see HotelNotFoundException
 */
@Service
public class HotelService {

    private final HotelRepository repository;
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;

    /**
     * Constructs a new instance of {@code HotelService} with the provided repositories.
     *
     * @param repository the {@link HotelRepository} to be used by this service.
     * @param reviewRepository the {@link ReviewRepository} for handling hotel reviews.
     * @param roomRepository the {@link RoomRepository} for handling hotel rooms.
     */
    public HotelService(HotelRepository repository, ReviewRepository reviewRepository, RoomRepository roomRepository) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Saves a new hotel to the database and converts it to a {@link HotelDTO}.
     *
     * @param hotel the hotel entity to be saved.
     * @return a {@link HotelDTO} representation of the saved hotel.
     */
    public HotelDTO save(Hotel hotel) {
        return toDTO(repository.save(hotel));
    }

    /**
     * Deletes the specified hotel and removes its associated rooms from the system.
     *
     * @param hotel the hotel to be deleted.
     * @throws HotelNotFoundException if the hotel is not found in the database.
     */
    @Transactional
    public void deleteHotel(Hotel hotel) {
        var existingHotel = repository.findHotelById(hotel.getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );
        for (Room room : existingHotel.getRooms()) {
            var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(existingHotel.getId(), room.getRoomNr()).orElseThrow(
                    () -> new HotelNotFoundException("Room " + room.getRoomNr() + " from hotel with id " + existingHotel.getId() + " not found")
            );
            existingHotel.removeRoom(room);
        }
        repository.deleteById(existingHotel.getId());
    }

    /**
     * Updates the details of an existing hotel.
     *
     * @param hotel the hotel with updated data.
     * @return a {@link HotelDTO} representation of the updated hotel.
     * @throws HotelNotFoundException if the hotel is not found in the database.
     */
    public HotelDTO updateHotel(Hotel hotel) {
        var existingHotel = repository.findHotelById(hotel.getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );

        if (hotel.getName() != null && !hotel.getName().isEmpty()) {
            existingHotel.setName(hotel.getName());
        }

        if (hotel.getOwner() != null && !hotel.getOwner().isEmpty()) {
            existingHotel.setOwner(hotel.getOwner());
        }

        if (hotel.getRegisterDate() != null) {
            existingHotel.setRegisterDate(hotel.getRegisterDate());
        }

        if (hotel.getCountry() != null && !hotel.getCountry().isEmpty()) {
            existingHotel.setCountry(hotel.getCountry());
        }

        if (hotel.getCity() != null && !hotel.getCity().isEmpty()) {
            existingHotel.setCity(hotel.getCity());
        }

        if (hotel.getAddress() != null && !hotel.getAddress().isEmpty()) {
            existingHotel.setAddress(hotel.getAddress());
        }

        return toDTO(repository.save(existingHotel));
    }

    /**
     * Retrieves a hotel by its ID and converts it to a {@link HotelDTO}.
     *
     * @param id the ID of the hotel to retrieve.
     * @return a {@link HotelDTO} representation of the found hotel.
     * @throws HotelNotFoundException if the hotel with the specified ID is not found.
     */
    public HotelDTO getHotelById(int id) {
        return toDTO(repository.findHotelById(id).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + id + " was not found")
        ));
    }

    /**
     * Retrieves a list of all hotels.
     *
     * @return a list of all hotels.
     */
    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    /**
     * Retrieves a paginated list of hotels based on filter criteria such as name, country, city, and rating.
     *
     * @param name the name of the hotel to search for.
     * @param countries a list of countries to filter the hotels.
     * @param cities a list of cities to filter the hotels.
     * @param lowerRatingLimit the minimum rating of the hotel.
     * @param upperRatingLimit the maximum rating of the hotel.
     * @param pageable the pagination information.
     * @return a page of {@link HotelDTO} objects matching the filter criteria.
     */
    public Page<HotelDTO> getHotelsBy(
            String name,
            List<String> countries,
            List<String> cities,
            Integer lowerRatingLimit,
            Integer upperRatingLimit,
            Pageable pageable) {

        Specification<Hotel> spec = Specification.where(HotelSpecification.hasName(name)
                .and(HotelSpecification.hasCity(cities))
                .and(HotelSpecification.hasCountry(countries))
                .and(HotelSpecification.hasRatingHigherOrEqual(lowerRatingLimit))
                .and(HotelSpecification.hasRatingLowerOrEqual(upperRatingLimit)));
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * Converts a {@link Hotel} entity to a {@link HotelDTO} representation.
     *
     * @param hotel the hotel entity to convert.
     * @return the corresponding {@link HotelDTO}.
     */
    private HotelDTO toDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setOwner(hotel.getOwner());
        dto.setRegisterDate(hotel.getRegisterDate());
        dto.setCountry(hotel.getCountry());
        dto.setCity(hotel.getCity());
        dto.setAddress(hotel.getAddress());

        Double rating = reviewRepository.findAverageRatingByHotelId(hotel.getId());
        dto.setRating(rating != null ? rating : 0.0);
        return dto;
    }
}
