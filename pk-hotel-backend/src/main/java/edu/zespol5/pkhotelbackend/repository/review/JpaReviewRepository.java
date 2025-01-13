package edu.zespol5.pkhotelbackend.repository.review;

import edu.zespol5.pkhotelbackend.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The {@code JpaReviewRepository} interface provides data access operations for the
 * {@link Review} entity. It extends the {@link JpaRepository} interface for standard CRUD
 * operations, the {@link JpaSpecificationExecutor} interface for dynamic query execution,
 * and integrates custom methods defined in the {@link ReviewRepository}.
 *
 * <p>
 * The {@link Repository} annotation marks this interface as a Spring-managed component
 * for data access.
 * </p>
 *
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @see ReviewRepository
 * @see Review
 */
@Repository
interface JpaReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review>, ReviewRepository {

}
