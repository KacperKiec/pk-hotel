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

@Service
public class HotelService {
    private final HotelRepository repository;
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;

    public HotelService(HotelRepository repository, ReviewRepository reviewRepository, RoomRepository roomRepository) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
        this.roomRepository = roomRepository;
    }

    public HotelDTO save(Hotel hotel) {
        return toDTO(repository.save(hotel));
    }

    @Transactional
    public void deleteHotel(Hotel hotel) {
        var exisitngHotel = repository.findHotelById(hotel.getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );
        for (Room room : exisitngHotel.getRooms()) {
            var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(exisitngHotel.getId(), room.getRoomNr()).orElseThrow(
                    () -> new HotelNotFoundException("Room " + room.getRoomNr() + " from hotel with id " + exisitngHotel.getId() +" not found")
            );
            exisitngHotel.removeRoom(room);
            //roomRepository.deleteRoomByRoomNrAndHotelId(existingRoom.getRoomNr(), existingRoom.getHotel().getId());
        }
        repository.deleteById(exisitngHotel.getId());
    }

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

    public HotelDTO getHotelById(int id) {
        return toDTO(repository.findHotelById(id).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + id + " was not found")
        ));
    }

    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

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
