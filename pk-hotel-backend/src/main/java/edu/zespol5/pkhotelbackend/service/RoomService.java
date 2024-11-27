package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.Room;
import edu.zespol5.pkhotelbackend.model.RoomStandard;
import edu.zespol5.pkhotelbackend.exception.RoomNotFoundException;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository repository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository repository, HotelRepository hotelRepository) {
        this.repository = repository;
        this.hotelRepository = hotelRepository;
    }

    public Room save(Room room) {
        return repository.save(room);
    }

    public Room getRoomById(int hotelId, int roomNr) {
        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );
        return repository.findRoomByHotel_IdAndRoomNr(hotelId, roomNr).orElseThrow(
                () -> new RoomNotFoundException("Room with id " + hotelId + "/" +roomNr + " was not found")
        );
    }

    public Page<Room> getAllRooms(Pageable pageable){
        return repository.findAll(pageable);
    }

    public List<Room> getRoomsBy(
            int hotelId,
            int lowerPriceLimit,
            int upperPriceLimit,
            RoomStandard standard,
            int places,
            LocalDate startDate,
            LocalDate endDate) {

        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );

        Specification<Room> spec = Specification.where(RoomSpecification.hasHotelId(hotelId))
                .and(RoomSpecification.hasPlaces(places))
                .and(RoomSpecification.hasPriceLessOrEqualThan(upperPriceLimit))
                .and(RoomSpecification.hasPriceGreaterOrEqualThan(lowerPriceLimit))
                .and(RoomSpecification.hasStandard(standard))
                .and(RoomSpecification.isAvailable(startDate, endDate));
        return repository.findAll(spec);
    }
}
