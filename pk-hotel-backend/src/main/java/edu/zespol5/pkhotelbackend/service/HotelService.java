package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.model.hotel.HotelDTO;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelSpecification;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository repository;
    private final ReviewRepository reviewRepository;

    public HotelService(HotelRepository repository, ReviewRepository reviewRepository) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
    }

    public HotelDTO save(Hotel hotel) {
        return toDTO(repository.save(hotel));
    }

    public void deleteHotel(Hotel hotel) {
        if(!repository.findHotelById(hotel.getId()).isPresent()) {
            throw new HotelNotFoundException("Hotel with id " + hotel.getId() + " not found");
        }
        repository.deleteById(hotel.getId());
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

    public Hotel getHotelById(int id) {
        return repository.findHotelById(id).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + id + " was not found")
        );
    }

    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    public List<Hotel> getHotelsBy(
            String name,
            List<String> countries,
            List<String> cities,
            Integer lowerRatingLimit,
            Integer upperRatingLimit) {

        Specification<Hotel> spec = Specification.where(HotelSpecification.hasName(name)
                .and(HotelSpecification.hasCity(cities))
                .and(HotelSpecification.hasCountry(countries))
                .and(HotelSpecification.hasRatingHigherOrEqual(lowerRatingLimit))
                .and(HotelSpecification.hasRatingLowerOrEqual(upperRatingLimit)));
        return repository.findAll(spec);
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
