package edu.zespol5.pkhotelbackend.repository.hotel;

import edu.zespol5.pkhotelbackend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JpaHotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel>, HotelRepository {

}
