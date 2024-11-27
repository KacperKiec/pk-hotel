package edu.zespol5.pkhotelbackend.repository.user;

import edu.zespol5.pkhotelbackend.model.User;
import edu.zespol5.pkhotelbackend.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findClientById(int id);
    Optional<User> findClientByEmail(String email);
    List<User> findAll();
    List<User> findAllByRole(UserRole role);
}
