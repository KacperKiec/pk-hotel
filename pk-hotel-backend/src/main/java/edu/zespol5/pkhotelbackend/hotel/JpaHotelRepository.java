package edu.zespol5.pkhotelbackend.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaHotelRepository extends JpaRepository<Hotel, Integer>, HotelRepository {
}