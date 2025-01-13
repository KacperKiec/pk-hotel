package edu.zespol5.pkhotelbackend.repository.extra;

import edu.zespol5.pkhotelbackend.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaExtraRepository} interface provides data access operations for the
 * {@link Extra} entity. It extends the {@link JpaRepository} interface for standard CRUD
 * operations, the {@link JpaSpecificationExecutor} interface for dynamic queries, and
 * integrates custom methods defined in {@link ExtraRepository}.
 *
 * <p>
 * The {@link Repository} annotation indicates that this is a Spring-managed data access
 * component.
 * </p>
 *
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @see ExtraRepository
 * @see Extra
 */
@Repository
interface JpaExtraRepository extends JpaRepository<Extra, Integer>, JpaSpecificationExecutor<Extra>, ExtraRepository {
}
