package edu.zespol5.pkhotelbackend.repository.user;

import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    List<User> findAll();
    List<User> findAllByRole(UserRole role);
}
