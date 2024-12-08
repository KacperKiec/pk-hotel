package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.IllegalOperationException;
import edu.zespol5.pkhotelbackend.exception.UserAlreadyExistsException;
import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.model.user.UserRole;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UserDTO registerUser(User user) {
        repository.findUserByEmail(user.getEmail()).ifPresent(evt -> {
            throw new UserAlreadyExistsException("User with given email already exists");
        });
        user.setPassword(encoder.encode(user.getPassword()));
        return toDTO(repository.save(user));
    }

    public UserDetails loadUserByUsername(String email) {
        var user = repository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with given email does not exist!")
        );

        logger.info("Znaleziono uzytkownika: {}", user);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()).toUpperCase())
                .build();
    }

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

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
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

    public UserDTO getUserByEmail(String email) {
        return toDTO(repository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " was not found")
        ));
    }

    public User getUserById(int id) {
        return repository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " was not found")
        );
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    public Page<UserDTO> getAllClients(Pageable pageable) {
        return repository.findAllByRole(UserRole.CLIENT, pageable).map(this::toDTO);
    }

    public Page<UserDTO> getAllAdmins(Pageable pageable) {
        return repository.findAllByRole(UserRole.ADMIN, pageable).map(this::toDTO);
    }

    public void deleteUser(User user, String currentUserEmail) {
        var existingUser = repository.findUserByEmail(user.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User with email " + user.getEmail() + " was not found")
        );

        if(currentUserEmail.equals(existingUser.getEmail()))
            throw new IllegalOperationException("You can not delete this user (admin). Only other admin can delete admin");

        repository.deleteByEmail(user.getEmail());
    }

    private UserDTO toDTO(User user){
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
