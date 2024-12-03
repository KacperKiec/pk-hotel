package edu.zespol5.pkhotelbackend.repository.user;

import edu.zespol5.pkhotelbackend.model.user.User;
import edu.zespol5.pkhotelbackend.model.user.UserDTO;
import edu.zespol5.pkhotelbackend.model.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    Page<User> findAll(Pageable pageable);
    Page<User> findAllByRole(UserRole role, Pageable pageable);
    void deleteByEmail(String email);
}
