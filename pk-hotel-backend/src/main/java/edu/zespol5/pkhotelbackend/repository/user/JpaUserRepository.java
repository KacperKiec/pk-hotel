package edu.zespol5.pkhotelbackend.repository.user;

import edu.zespol5.pkhotelbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaUserRepository extends JpaRepository<User, Integer>, UserRepository {

}
