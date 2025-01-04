package edu.zespol5.pkhotelbackend.service;

import edu.zespol5.pkhotelbackend.exception.ImageNotFoundException;
import edu.zespol5.pkhotelbackend.model.Image;
import edu.zespol5.pkhotelbackend.repository.image.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void deleteImage(int id) {
        var existingImage = imageRepository.findImageById(id).orElseThrow(
                () -> new ImageNotFoundException("Image with id " + id + " not found")
        );
        imageRepository.deleteById(existingImage.getId());
    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
