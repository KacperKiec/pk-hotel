package edu.zespol5.pkhotelbackend.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents an image associated with a hotel room or service.
 * <p>
 * The `Image` class is used to store information about images that can be associated with rooms or other services
 * within the hotel. Each image has a unique identifier and a path pointing to where the image is stored.
 * </p>
 */
@Data
@Entity
@Table(name = "image")
public class Image {

    /**
     * Unique identifier for the image.
     * This is the primary key used to uniquely identify the image in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The path where the image is stored.
     * This could be a URL or a local file path pointing to the image file.
     */
    private String path;
}
