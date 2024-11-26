package edu.zespol5.pkhotelbackend.reservation;

import edu.zespol5.pkhotelbackend.client.ClientNotFoundException;
import edu.zespol5.pkhotelbackend.client.ClientRepository;
import edu.zespol5.pkhotelbackend.hotel.HotelNotFoundException;
import edu.zespol5.pkhotelbackend.hotel.HotelRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final ClientRepository clientRepository;
    private final HotelRepository hotelRepository;

    public ReservationService(ReservationRepository repository, ClientRepository clientRepository, HotelRepository hotelRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.hotelRepository = hotelRepository;
    }

    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation getReservationById(int id) {
        return repository.findReservationById(id).orElseThrow(
                () -> new ReservationNotFoundException("Reservation with id " + id + " was not found")
        );
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public List<Reservation> getReservationsBy(
            ReservationStatus status,
            int hotelId,
            int clientId) {

        hotelRepository.findHotelById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with id " + hotelId + " was not found")
        );

        clientRepository.findClientById(clientId).orElseThrow(
                () -> new ClientNotFoundException("Client with id " + clientId + " was not found")
        );

        Specification<Reservation> spec = Specification.where(ReservationSpecification.hasStatus(status)
                .and(ReservationSpecification.hasHotelId(hotelId)))
                .and(ReservationSpecification.hasClientId(clientId));
        return repository.findAll(spec);
    }
}
