package edu.zespol5.pkhotelbackend.repository.reservation;

import edu.zespol5.pkhotelbackend.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaReservationRepository} interface provides CRUD operations and dynamic query
 * execution for the {@link Reservation} entity using Spring Data JPA.
 *
 * <p>
 * This interface extends {@link JpaRepository} for basic CRUD operations,
 * {@link JpaSpecificationExecutor} for executing dynamic queries with specifications, and
 * {@link ReservationRepository} to inherit the custom repository methods for reservations.
 * </p>
 *
 * @see Reservation
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
@Repository
interface JpaReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation>, ReservationRepository {

}
