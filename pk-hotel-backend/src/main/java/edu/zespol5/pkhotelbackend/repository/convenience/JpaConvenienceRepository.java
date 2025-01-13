package edu.zespol5.pkhotelbackend.repository.convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaConvenienceRepository} interface is responsible for data access operations
 * related to the {@link Convenience} entity. It extends the {@link JpaRepository} interface,
 * providing standard CRUD operations, and integrates additional methods defined in
 * {@link ConvenienceRepository}.
 *
 * <p>
 * The {@link Repository} annotation indicates that this is a data access layer component
 * managed automatically by the Spring Framework.
 * </p>
 *
 * <p>
 * This interface provides methods for performing database operations such as saving, deleting,
 * and retrieving {@link Convenience} entities. Custom methods can also be defined in the
 * {@link ConvenienceRepository} interface and implemented here.
 * </p>
 *
 * @see JpaRepository
 * @see ConvenienceRepository
 * @see Convenience
 */
@Repository
interface JpaConvenienceRepository extends JpaRepository<Convenience, Integer>, ConvenienceRepository {
}
