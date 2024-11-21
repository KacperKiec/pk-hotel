package edu.zespol5.pkhotelbackend.client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findClientById(int id);
    Optional<Client> findClientByEmail(String email);
    List<Client> findAll();
}
