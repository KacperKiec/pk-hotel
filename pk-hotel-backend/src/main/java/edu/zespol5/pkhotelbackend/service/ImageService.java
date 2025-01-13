package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ImageNotFoundException;
import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.repository.image.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code ImageService} provides business logic for managing images in the system.
 * It includes methods for deleting and fetching images. The service interacts with
 * the {@link ImageRepository} for accessing image data.
 *
 * <p>
 * This service is responsible for deleting an image by its ID, retrieving all images from the system,
 * and ensuring that appropriate exceptions are thrown when images are not found.
 * </p>
 *
 * @see Image
 * @see ImageRepository
 * @see ImageNotFoundException
 */
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    /**
     * Constructs a new instance of {@code ImageService} with the provided {@link ImageRepository}.
     *
     * @param imageRepository the {@link ImageRepository} to be used by this service.
     */
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Deletes an image from the database by its ID.
     *
     * @param id the ID of the image to delete.
     * @throws ImageNotFoundException if the image with the specified ID is not found.
     */
    @Transactional
    public void deleteImage(int id) {
        var existingImage = imageRepository.findImageById(id).orElseThrow(
                () -> new ImageNotFoundException("Image with id " + id + " not found")
        );
        imageRepository.deleteById(existingImage.getId());
    }

    /**
     * Retrieves all images from the system.
     *
     * @return a list of all images.
     */
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
