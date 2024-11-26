package edu.zespol5.pkhotelbackend.convenience;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvenienceService {
    private final ConvenienceRepository repository;

    public ConvenienceService(ConvenienceRepository repository) {
        this.repository = repository;
    }

    public Convenience save(Convenience convenience) {
        return repository.save(convenience);
    }

    public Convenience getConvenienceById(int id) {
        return repository.findConvenienceById(id).orElseThrow(
                () -> new ConvenienceNotFoundException("Convenience id " + id + "was not found")
        );
    }

    public List<Convenience> getConveniencesByName(String name) {
        return repository.findConveniencesByName(name);
    }

    public List<Convenience> getAllConveniences() {
        return repository.findAll();
    }
}
