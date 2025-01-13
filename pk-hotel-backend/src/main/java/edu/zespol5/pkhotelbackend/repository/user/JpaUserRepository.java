package edu.zespol5.pkhotelbackend.repository.user;

import edu.zespol5.pkhotelbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code JpaUserRepository} is a repository interface that extends the
 * {@link JpaRepository} and {@link UserRepository} interfaces for performing
 * CRUD operations and custom queries on the {@link User} entity.
 *
 * <p>
 * It allows for easy interaction with the database for user-related operations
 * such as saving, deleting, and retrieving users, as well as implementing custom
 * queries defined in the {@link UserRepository}.
 * </p>
 *
 * @see JpaRepository
 * @see UserRepository
 * @see User
 */
@Repository
interface JpaUserRepository extends JpaRepository<User, Integer>, UserRepository {
}
