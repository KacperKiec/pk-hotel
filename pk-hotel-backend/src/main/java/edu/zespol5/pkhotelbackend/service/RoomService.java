package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ConvenienceNotFoundException;
import edu.zespol5.pkhotelbackend.exception.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.exception.ImageNotFoundException;
import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.model.room_convenience.RoomConvenience;
import edu.zespol5.pkhotelbackend.model.room.RoomDTO;
import edu.zespol5.pkhotelbackend.model.room_image.RoomImage;
import edu.zespol5.pkhotelbackend.repository.convenience.ConvenienceRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.room.RoomStandard;
import edu.zespol5.pkhotelbackend.exception.RoomNotFoundException;
import edu.zespol5.pkhotelbackend.repository.image.ImageRepository;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ConvenienceRepository convenienceRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, ConvenienceRepository convenienceRepository, ReviewRepository reviewRepository, ImageRepository imageRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.convenienceRepository = convenienceRepository;
        this.reviewRepository = reviewRepository;
        this.imageRepository = imageRepository;
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

            if(!existingRoom.getConveniences().contains(roomConvenience)) {
                existingRoom.addConvenience(roomConvenience);
            }
        }

        return toDTO(roomRepository.save(existingRoom));
    }

    @Transactional
    public void removeConvenience(Room room, Integer convenienceId) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        var convenience = existingRoom.getConveniences().stream()
                .filter(conv -> conv.getConvenience().getId().equals(convenienceId))
                .findFirst()
                .orElseThrow(
                        () -> new ConvenienceNotFoundException("Convenience not found")
                );

        existingRoom.removeConvenience(convenience);
        roomRepository.save(existingRoom);
    }

    @Transactional
    public RoomDTO addImages(Room room, List<Image> images) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );


        for (Image img : images) {

            if(img.getPath() != null && !img.getPath().isEmpty()){
                var temp = imageRepository.save(img);
                RoomImage roomImage = new RoomImage(room, temp);
                existingRoom.addImage(roomImage);
            }
        }

        return toDTO(roomRepository.save(existingRoom));
    }

    @Transactional
    public void removeImage(Room room, Integer imageId) {
        var existingRoom = roomRepository.findRoomByHotel_IdAndRoomNr(room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );

        var image = existingRoom.getImages().stream()
                .filter(img -> img.getImage().getId().equals(imageId))
                .findFirst()
                .orElseThrow(
                        () -> new ImageNotFoundException("Image with id " + imageId + " was not found")
                );

        existingRoom.removeImage(image);
        imageRepository.deleteById(image.getImage().getId());
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
            Integer roomNr,
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
                .and(RoomSpecification.hasRoomNr(roomNr))
                .and(RoomSpecification.hasPlaces(places))
                .and(RoomSpecification.hasPriceLessOrEqualThan(upperPriceLimit))
                .and(RoomSpecification.hasPriceGreaterOrEqualThan(lowerPriceLimit))
                .and(RoomSpecification.hasStandard(standard))
                .and(RoomSpecification.isAvailable(startDate, endDate));
        return roomRepository.findAll(spec, pageable).map(this::toDTO);
    }

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

    public void deleteRoom(Room room) {
        roomRepository.findRoomByHotel_IdAndRoomNr(
                room.getHotel().getId(), room.getRoomNr()).orElseThrow(
                () -> new RoomNotFoundException("Room not found")
        );
        roomRepository.deleteRoomByRoomNrAndHotelId(room.getRoomNr(), room.getHotel().getId());
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

        Double rating = reviewRepository.findAverageRatingByHotelId(room.getHotel().getId());

        dto.setRating(rating != null ? rating : 0.0);
        dto.setImages(imageRepository.findImageByRoomId(room.getRoomNr(), room.getHotel().getId()));

        return dto;
    }
}
