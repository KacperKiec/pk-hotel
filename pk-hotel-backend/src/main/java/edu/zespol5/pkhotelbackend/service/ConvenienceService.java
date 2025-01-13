package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ConvenienceNotFoundException;
import edu.zespol5.pkhotelbackend.exception.ResourceAlreadyExistsException;
import edu.zespol5.pkhotelbackend.repository.convenience.ConvenienceRepository;
import edu.zespol5.pkhotelbackend.model.Convenience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * {@code ConvenienceService} provides business logic for managing conveniences.
 * It handles CRUD operations and ensures that constraints, such as uniqueness of
 * convenience names, are enforced. The service interacts with the {@link ConvenienceRepository}
 * to access and modify convenience data in the database.
 *
 * <p>
 * The service includes methods to save, update, delete, and fetch conveniences by their
 * identifiers or names. If a convenience is not found, an appropriate exception is thrown.
 * </p>
 *
 * @see Convenience
 * @see ConvenienceRepository
 * @see ConvenienceNotFoundException
 * @see ResourceAlreadyExistsException
 */
@Service
public class ConvenienceService {

    private final ConvenienceRepository repository;

    /**
     * Constructs a new instance of {@code ConvenienceService} with the provided repository.
     *
     * @param repository the {@link ConvenienceRepository} to be used by this service.
     */
    public ConvenienceService(ConvenienceRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a new convenience to the database if a convenience with the same name does not already exist.
     *
     * @param convenience the {@link Convenience} entity to be saved.
     * @return the saved convenience entity.
     * @throws ResourceAlreadyExistsException if a convenience with the same name already exists.
     */
    public Convenience save(Convenience convenience) {
        if (repository.findConveniencesByName(convenience.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Convenience with name " + convenience.getName() + " already exists. Choose one from existing conveniences");
        }
        return repository.save(convenience);
    }

    /**
     * Retrieves all conveniences in a paginated manner.
     *
     * @param pageable the pagination information.
     * @return a page of conveniences.
     */
    public Page<Convenience> getAllConveniences(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Deletes the specified convenience from the database.
     *
     * @param convenience the convenience to be deleted.
     * @throws ConvenienceNotFoundException if the specified convenience does not exist in the database.
     */
    public void deleteConvenience(Convenience convenience) {
        repository.findConvenienceById(convenience.getId()).orElseThrow(
                () -> new ConvenienceNotFoundException("Convenience with id " + convenience.getId() + " not found")
        );

        repository.deleteById(convenience.getId());
    }

    /**
     * Updates an existing convenience in the database.
     *
     * @param convenience the convenience with updated data.
     * @return the updated convenience entity.
     * @throws ConvenienceNotFoundException if the specified convenience does not exist in the database.
     */
    public Convenience updateConvenience(Convenience convenience) {
        repository.findConvenienceById(convenience.getId()).orElseThrow(
                () -> new ConvenienceNotFoundException("Convenience with id " + convenience.getId() + " not found")
        );

        if (convenience.getName() != null) {
            convenience.setName(convenience.getName());
        }

        return repository.save(convenience);
    }

    /**
     * Retrieves a convenience by its ID.
     *
     * @param id the ID of the convenience to retrieve.
     * @return the found convenience entity.
     * @throws ConvenienceNotFoundException if the convenience with the specified ID is not found.
     */
    public Convenience getConvenienceById(int id) {
        return repository.findConvenienceById(id).orElseThrow(
                () -> new ConvenienceNotFoundException("Convenience id " + id + " was not found")
        );
    }

    /**
     * Retrieves a convenience by its name.
     *
     * @param name the name of the convenience to retrieve.
     * @return the found convenience entity.
     * @throws ConvenienceNotFoundException if the convenience with the specified name is not found.
     */
    public Convenience getConveniencesByName(String name) {
        return repository.findConveniencesByName(name).orElseThrow(
                () -> new ConvenienceNotFoundException("Convenience with name " + name + " not found")
        );
    }
}
