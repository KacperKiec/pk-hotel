package edu.zespol5.pkhotelbackend.repository.hotel;

import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface HotelRepository {
    Hotel save(Hotel hotel);
    Optional<Hotel> findHotelById(int id);
    List<Hotel> findAll();
    List<Hotel> findAll(Specification<Hotel> spec);
    void deleteById(int id);
}
