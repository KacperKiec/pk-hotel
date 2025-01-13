package edu.zespol5.pkhotelbackend.repository.hotel;

import edu.zespol5.pkhotelbackend.model.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaHotelRepository} interface provides data access operations for the
 * {@link Hotel} entity. It extends the {@link JpaRepository} interface for standard CRUD
 * operations, the {@link JpaSpecificationExecutor} interface for dynamic query execution,
 * and integrates custom methods defined in {@link HotelRepository}.
 *
 * <p>
 * The {@link Repository} annotation marks this as a Spring-managed component for data access.
 * </p>
 *
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @see HotelRepository
 * @see Hotel
 */
@Repository
interface JpaHotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel>, HotelRepository {
}
