package edu.zespol5.pkhotelbackend.repository.image;

import edu.zespol5.pkhotelbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaImageRepository extends JpaRepository<Image, Integer>, ImageRepository {

}
