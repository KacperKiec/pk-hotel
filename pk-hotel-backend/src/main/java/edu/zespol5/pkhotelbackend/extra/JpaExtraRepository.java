package edu.zespol5.pkhotelbackend.extra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaExtraRepository extends JpaRepository<Extra, Integer>, ExtraRepository {
}
