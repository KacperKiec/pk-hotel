package edu.zespol5.pkhotelbackend.repository.convenience;

import edu.zespol5.pkhotelbackend.model.Convenience;

import java.util.List;
import java.util.Optional;

public interface ConvenienceRepository {
    Convenience save(Convenience convenience);
    Optional<Convenience> findConvenienceById(int id);
    List<Convenience> findAll();
    List<Convenience> findConveniencesByName(String name);
}
