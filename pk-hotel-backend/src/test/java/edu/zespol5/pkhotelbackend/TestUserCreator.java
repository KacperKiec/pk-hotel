package edu.zespol5.pkhotelbackend;

import edu.zespol5.pkhotelbackend.model.Extra;
import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import edu.zespol5.pkhotelbackend.model.reservation.ReservationStatus;
import edu.zespol5.pkhotelbackend.model.review.Review;
import edu.zespol5.pkhotelbackend.model.room.Room;
import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.model.user.UserRole;
import edu.zespol5.pkhotelbackend.repository.extra.ExtraRepository;
import edu.zespol5.pkhotelbackend.repository.hotel.HotelRepository;
import edu.zespol5.pkhotelbackend.repository.reservation.ReservationRepository;
import edu.zespol5.pkhotelbackend.repository.review.ReviewRepository;
import edu.zespol5.pkhotelbackend.repository.room.RoomRepository;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import edu.zespol5.pkhotelbackend.service.ReservationService;
import edu.zespol5.pkhotelbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@SpringBootTest
public class TestUserCreator {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ExtraRepository extraRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void createMultipleTestUsers() {

        LocalDate startDate = LocalDate.parse("1980-01-01");
        LocalDate endDate = LocalDate.parse("2003-01-01");

        for (int i = 1; i <= 100; i++) {
            String firstName = "User" + i;
            String lastName = "Test" + i;
            String email = "user" + i + "@example.com";

            createTestUser(firstName, lastName, email, UserRole.CLIENT, startDate, endDate);
        }
    }

    private UserDTO createTestUser(String firstName, String lastName, String email, UserRole role, LocalDate startDate, LocalDate endDate) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword("passs");
        user.setRole(role);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(0, daysBetween + 1);
        user.setBirthDate(startDate.plusDays(randomDays));

        return userService.registerUser(user);
    }

    @Test
    public void addSampleReservations() {

        List<User> users = userRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<Extra> extras = extraRepository.findAll();

        for (int i = 0; i < 200; i++) {
            User user = users.get(i % users.size());
            Room room = rooms.get(i % rooms.size());
            Extra extra = extras.get(i % extras.size());

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setRoom(room);
            reservation.setCheckInDate(LocalDate.now().plusDays(i));
            reservation.setCheckOutDate(LocalDate.now().plusDays(i + 5));
            reservation.setStatus(ReservationStatus.ACCEPTED);

            System.out.println(reservationService.save(reservation, List.of(extra.getId()), user.getEmail()));

        }
        Assertions.assertNotNull(reservationRepository.findAll());
    }

    private static final String[] REVIEW_TEXTS = {
            "The hotel was fantastic, had an amazing experience!",
            "I had a pleasant stay, but the service could be better.",
            "The room was clean, but the location was not ideal.",
            "Very disappointed with the hotel. Would not recommend.",
            "Great value for the price. The staff was friendly and helpful.",
            "The hotel was okay, but there were a few issues that could be improved.",
            "An excellent stay! I would love to come back again."
    };

    @Test
    public void generateReviews() {
        List<Reservation> reservations = reservationRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < 200; i++) {

            Reservation reservation = reservations.get(random.nextInt(reservations.size()));

            User user = reservation.getUser();
            Hotel hotel = reservation.getRoom().getHotel();

            int rating = random.nextInt(5) + 1;

            String reviewText = REVIEW_TEXTS[random.nextInt(REVIEW_TEXTS.length)];

            Review review = new Review();
            review.setUser(user);
            review.setHotel(hotel);
            review.setRating(rating);
            review.setContent(reviewText);

            reviewRepository.save(review);
        }
    }
}

