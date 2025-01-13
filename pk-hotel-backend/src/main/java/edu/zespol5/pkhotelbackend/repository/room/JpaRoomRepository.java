package edu.zespol5.pkhotelbackend.repository.room;

import edu.zespol5.pkhotelbackend.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaRoomRepository} interface provides CRUD operations and dynamic query
 * execution for the {@link Room} entity using Spring Data JPA.
 *
 * <p>
 * This interface extends {@link JpaRepository} for basic CRUD operations,
 * {@link JpaSpecificationExecutor} for executing dynamic queries with specifications, and
 * {@link RoomRepository} to inherit custom repository methods for rooms.
 * </p>
 *
 * @see Room
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
@Repository
interface JpaRoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room>, RoomRepository {

}
