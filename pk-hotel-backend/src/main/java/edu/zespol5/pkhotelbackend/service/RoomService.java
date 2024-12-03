package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ConvenienceNotFoundException;
import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.model.Convenience;
import edu.zespol5.pkhotelbackend.model.connectors.RoomConvenience;
import edu.zespol5.pkhotelbackend.model.connectors.RoomConvenienceId;
import edu.zespol5.pkhotelbackend.model.room.RoomDTO;
import edu.zespol5.pkhotelbackend.model.room.RoomId;
import edu.zespol5.pkhotelbackend.repository.convenience.ConvenienceRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomStandard;
import edu.zespol5.pkhotelbackend.exception.RoomNotFoundException;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ConvenienceRepository convenienceRepository;
    private final ReviewRepository reviewRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, ConvenienceRepository convenienceRepository, ReviewRepository reviewRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.convenienceRepository = convenienceRepository;
        this.reviewRepository = reviewRepository;
    }

    public RoomDTO saveRoom(Room room) {
        hotelRepository.findHotelById(room.getHotel().getId()).orElseThrow(
                () -> new HotelNotFoundException("Hotel not found")
        );
        return toDTO(roomRepository.save(room));
    }

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
            existingRoom.addConvenience(roomConvenience);
        }

        return toDTO(roomRepository.save(existingRoom));
    }

    @Transactional
    public void removeConvenience(Room room, int convenienceId) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        var convenience = existingRoom.getConveniences().stream()
                .filter(conv -> conv.getConvenience().getId() == convenienceId)
                .findFirst()
                .orElseThrow(
                        () -> new ConvenienceNotFoundException("Convenience not found")
                );

        existingRoom.removeConvenience(convenience);
        roomRepository.save(existingRoom);
    }

    public RoomDTO getRoomById(int hotelId, int roomNr) {
        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );
        return toDTO(roomRepository.findRoomByHotel_IdAndRoomNr(hotelId, roomNr).orElseThrow(
                () -> new RoomNotFoundException("Room with id " + hotelId + "/" +roomNr + " was not found")
        ));
    }

    public Page<RoomDTO> getAllRooms(Pageable pageable){
        return roomRepository.findAll(pageable).map(this::toDTO);
    }

    public Page<RoomDTO> getRoomsBy(
            Integer hotelId,
            Double lowerPriceLimit,
            Double upperPriceLimit,
            RoomStandard standard,
            Integer places,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );

        Specification<Room> spec = Specification.where(RoomSpecification.hasHotelId(hotelId))
                .and(RoomSpecification.hasPlaces(places))
                .and(RoomSpecification.hasPriceLessOrEqualThan(upperPriceLimit))
                .and(RoomSpecification.hasPriceGreaterOrEqualThan(lowerPriceLimit))
                .and(RoomSpecification.hasStandard(standard))
                .and(RoomSpecification.isAvailable(startDate, endDate));
        return roomRepository.findAll(spec, pageable).map(this::toDTO);
    }

    private RoomDTO toDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomNr(room.getRoomNr());
        dto.setHotelName(room.getHotel().getName());
        dto.setPlaces(room.getPlaces());
        dto.setPrice(room.getPrice());
        dto.setStandard(room.getStandard());
        dto.setDescription(room.getDescription());
        dto.setConveniences(convenienceRepository.findConvenienceByRoomId(room.getRoomNr(), room.getHotel().getId()));
        dto.setRating(reviewRepository.findAverageRatingByHotelId(room.getHotel().getId()));

        return dto;
    }
}
