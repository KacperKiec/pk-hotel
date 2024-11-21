package edu.zespol5.pkhotelbackend.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaClientRepository extends JpaRepository<Client, Integer>, ClientRepository {

}
