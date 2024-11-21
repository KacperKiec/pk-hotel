package edu.zespol5.pkhotelbackend.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JpaRoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room>, RoomRepository {
}
