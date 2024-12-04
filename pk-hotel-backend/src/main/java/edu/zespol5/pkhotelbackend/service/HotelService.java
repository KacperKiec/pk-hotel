package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.model.hotel.HotelDTO;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelSpecification;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
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
        return dto;
    }
}
