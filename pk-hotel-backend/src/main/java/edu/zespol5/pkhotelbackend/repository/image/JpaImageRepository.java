package edu.zespol5.pkhotelbackend.repository.image;

import edu.zespol5.pkhotelbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaImageRepository} interface provides data access operations for the
 * {@link Image} entity. It extends the {@link JpaRepository} interface for standard CRUD
 * operations and integrates custom methods defined in the {@link ImageRepository}.
 *
 * <p>
 * The {@link Repository} annotation marks this as a Spring-managed component for data access.
 * </p>
 *
 * @see JpaRepository
 * @see ImageRepository
 * @see Image
 */
@Repository
public interface JpaImageRepository extends JpaRepository<Image, Integer>, ImageRepository {

}
