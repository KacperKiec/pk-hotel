package edu.zespol5.pkhotelbackend.convenience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaConvenienceRepository extends JpaRepository<Convenience, Integer>, ConvenienceRepository {
}
