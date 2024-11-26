package edu.zespol5.pkhotelbackend.client;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client save(Client client) {
        return repository.save(client);
    }

    public Client getClientById(int id) {
        return repository.findClientById(id).orElseThrow(
                () -> new ClientNotFoundException("Client with id " + id + " was not found")
        );
    }

    public Client getClientByEmail(String email) {
        return repository.findClientByEmail(email).orElseThrow(
                () -> new ClientNotFoundException("Client with email " + email + " was not found")
        );
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }
}
