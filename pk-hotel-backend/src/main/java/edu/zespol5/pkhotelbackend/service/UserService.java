package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.IllegalOperationException;
import edu.zespol5.pkhotelbackend.exception.UserAlreadyExistsException;
import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.model.user.UserRole;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * {@code UserService} is a service class that handles user-related operations such as registration, updating,
 * deletion, and retrieval of user information. It also integrates with Spring Security for user authentication.
 *
 * <p>
 * This service includes the following operations:
 * <ul>
 *   <li>User registration with password encoding and email verification.</li>
 *   <li>Loading user details for authentication purposes.</li>
 *   <li>Updating user details such as email, password, name, and role.</li>
 *   <li>Fetching user details by email or ID.</li>
 *   <li>Managing users' roles, such as distinguishing between clients and admins.</li>
 *   <li>Deleting users while enforcing restrictions on deleting the current authenticated user.</li>
 * </ul>
 * </p>
 *
 * @see User
 * @see UserDTO
 * @see UserRole
 * @see UserRepository
 */
@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Constructs a new {@code UserService} with the provided repository and password encoder.
     *
     * @param repository the repository used to access user data.
     * @param encoder the password encoder used to encode user passwords.
     */
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * Registers a new user by checking if the user already exists, encoding their password, and saving them to the repository.
     *
     * @param user the user to register.
     * @return a {@link UserDTO} containing the details of the registered user.
     * @throws UserAlreadyExistsException if a user with the provided email already exists.
     */
    public UserDTO registerUser(User user) {
        repository.findUserByEmail(user.getEmail()).ifPresent(evt -> {
            throw new UserAlreadyExistsException("User with given email already exists");
        });
        user.setPassword(encoder.encode(user.getPassword()));
        return toDTO(repository.save(user));
    }

    /**
     * Loads a user by their email for authentication purposes.
     *
     * @param email the email of the user to load.
     * @return the {@link UserDetails} for the user, which contains authentication and authorization information.
     * @throws UserNotFoundException if no user with the given email exists.
     */
    public UserDetails loadUserByUsername(String email) {
        var user = repository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with given email does not exist!")
        );

        logger.info("Found user: {}", user);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()).toUpperCase())
                .build();
    }

    /**
     * Updates the details of an existing user, including their email, first name, last name, password, birth date, and role.
     *
     * @param user the user with updated information.
     * @return a {@link UserDTO} containing the updated user details.
     * @throws UserNotFoundException if the user is not found.
     * @throws UserAlreadyExistsException if the email provided is already in use by another user.
     */
    public UserDTO updateUser(User user) {
        var existingUser = repository.findUserById(user.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        if(user.getEmail() != null && !user.getEmail().isEmpty() &&
                !user.getEmail().equals(existingUser.getEmail())) {
            var email = repository.findUserByEmail(user.getEmail());
            if(email.isPresent())
                throw new UserAlreadyExistsException("User with given email already exists");
            existingUser.setEmail(user.getEmail());
        }

        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            existingUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            existingUser.setLastName(user.getLastName());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(encoder.encode(user.getPassword()));
        }

        if (user.getBirthDate() != null) {
            existingUser.setBirthDate(user.getBirthDate());
        }

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        var savedUser = repository.save(existingUser);
        return toDTO(savedUser);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email of the user to retrieve.
     * @return a {@link UserDTO} containing the user details.
     * @throws UserNotFoundException if no user with the given email exists.
     */
    public UserDTO getUserByEmail(String email) {
        return toDTO(repository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " was not found")
        ));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the {@link User} object representing the user.
     * @throws UserNotFoundException if no user with the given ID exists.
     */
    public User getUserById(int id) {
        return repository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " was not found")
        );
    }

    /**
     * Retrieves all users with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link UserDTO} objects containing the user details.
     */
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Retrieves all users with the role of CLIENT with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link UserDTO} objects containing the client details.
     */
    public Page<UserDTO> getAllClients(Pageable pageable) {
        return repository.findAllByRole(UserRole.CLIENT, pageable).map(this::toDTO);
    }

    /**
     * Retrieves all users with the role of ADMIN with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link UserDTO} objects containing the admin details.
     */
    public Page<UserDTO> getAllAdmins(Pageable pageable) {
        return repository.findAllByRole(UserRole.ADMIN, pageable).map(this::toDTO);
    }

    /**
     * Deletes a user by their email address, restricting deletion of the currently authenticated admin user.
     *
     * @param user the user to delete.
     * @param currentUserEmail the email of the currently authenticated user.
     * @throws UserNotFoundException if the user to delete is not found.
     * @throws IllegalOperationException if the current user attempts to delete themselves.
     */
    @Transactional
    public void deleteUser(User user, String currentUserEmail) {
        var existingUser = repository.findUserByEmail(user.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User with email " + user.getEmail() + " was not found")
        );

        if(currentUserEmail.equals(existingUser.getEmail()))
            throw new IllegalOperationException("You cannot delete this user (admin). Only another admin can delete an admin.");

        repository.deleteByEmail(user.getEmail());
    }

    /**
     * Converts a {@link User} to a {@link UserDTO}.
     *
     * @param user the user to convert.
     * @return a {@link UserDTO} containing the user's details.
     */
    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setRole(user.getRole());
        return dto;
    }
}
