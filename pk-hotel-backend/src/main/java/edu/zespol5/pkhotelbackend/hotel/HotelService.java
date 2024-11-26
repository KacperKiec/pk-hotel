package edu.zespol5.pkhotelbackend.hotel;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public Hotel save(Hotel hotel) {
        return repository.save(hotel);
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
            int lowerRatingLimit,
            int upperRatingLimit) {

        Specification<Hotel> spec = Specification.where(HotelSpecification.hasName(name)
                .and(HotelSpecification.hasCity(cities))
                .and(HotelSpecification.hasCountry(countries))
                .and(HotelSpecification.hasRatingHigherOrEqual(lowerRatingLimit))
                .and(HotelSpecification.hasRatingLowerOrEqual(upperRatingLimit)));
        return repository.findAll(spec);
    }
}
