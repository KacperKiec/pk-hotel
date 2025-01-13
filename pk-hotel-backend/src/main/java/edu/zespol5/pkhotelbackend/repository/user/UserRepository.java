package edu.zespol5.pkhotelbackend.repository.user;

import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.model.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * {@code UserRepository} is a repository interface for performing CRUD operations and custom queries
 * on the {@link User} entity. It provides methods to save, find, and delete users, as well as retrieving
 * users by their role and email. This interface extends the capabilities provided by Spring Data JPA.
 *
 * <p>
 * It includes pagination and filtering based on user role and other properties.
 * </p>
 *
 * <p>
 * This interface is intended to be used for user-related operations such as account management,
 * searching by role, or deleting users based on email.
 * </p>
 *
 * @see User
 * @see UserDTO
 * @see UserRole
 */
public interface UserRepository {

    /**
     * Saves the provided {@link User} entity to the database.
     *
     * @param user the user entity to save.
     * @return the saved user entity.
     */
    User save(User user);

    /**
     * Finds a {@link User} by its ID.
     *
     * @param id the ID of the user to find.
     * @return an {@link Optional} containing the user if found, otherwise empty.
     */
    Optional<User> findUserById(int id);

    /**
     * Finds a {@link User} by its email.
     *
     * @param email the email of the user to find.
     * @return an {@link Optional} containing the user if found, otherwise empty.
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Finds all users and returns a paginated result.
     *
     * @param pageable the pagination information.
     * @return a page of {@link User} entities.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Finds all users and returns a list.
     *
     * @return a page of {@link User} entities.
     */
    List<User> findAll();

    /**
     * Finds all users with a specific role and returns a paginated result.
     *
     * @param role the role of the users to find.
     * @param pageable the pagination information.
     * @return a page of {@link User} entities matching the specified role.
     */
    Page<User> findAllByRole(UserRole role, Pageable pageable);

    /**
     * Deletes a {@link User} by its email.
     *
     * @param email the email of the user to delete.
     */
    void deleteByEmail(String email);
}
