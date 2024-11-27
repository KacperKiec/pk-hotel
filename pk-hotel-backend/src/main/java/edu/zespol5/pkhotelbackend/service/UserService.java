package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.config.JwtTokenUtil;
import edu.zespol5.pkhotelbackend.exception.UserAlreadyExistsException;
import edu.zespol5.pkhotelbackend.exception.UserAuthenticationFailedException;
import edu.zespol5.pkhotelbackend.exception.UserNotFoundException;
import edu.zespol5.pkhotelbackend.model.User;
import edu.zespol5.pkhotelbackend.model.UserDTO;
import edu.zespol5.pkhotelbackend.model.UserRole;
import edu.zespol5.pkhotelbackend.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final JwtTokenUtil jwtTokenUtil;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository, JwtTokenUtil jwtTokenUtil) {
        this.repository = repository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserDTO registerUser(User user) {
        repository.findClientByEmail(user.getEmail()).ifPresent(evt -> {
            throw new UserAlreadyExistsException("User with given email already exists");
        });
        return toDTO(repository.save(user));
    }

    public String loginUser(String email, String password) {
        logger.info("poczatek");
        var user = repository.findClientByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with given email does not exist")
        );
        if (!user.getPassword().equals(password))
            throw new UserAuthenticationFailedException("Incorrect password");

        var token = jwtTokenUtil.generateToken(user);
        logger.info("Generated token: " + token);
        return token;
    }

    public User getUserById(int id) {
        return repository.findClientById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " was not found")
        );
    }

    public UserDTO getUserByEmail(String email) {
        return toDTO(repository.findClientByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " was not found")
        ));
    }

    public List<User> getAllClients() {
        return repository.findAllByRole(UserRole.CLIENT);
    }

    private UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        return dto;
    }
}
