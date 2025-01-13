package edu.zespol5.pkhotelbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main entry point for the PK Hotel Backend application.
 * This class initializes the Spring Boot application and enables scheduling.
 *
 * <p>
 * It is annotated with {@link SpringBootApplication} to mark it as the primary Spring Boot configuration class.
 * The {@link EnableScheduling} annotation allows scheduling tasks, such as scheduled jobs, to be executed within the application.
 * </p>
 *
 * <p>
 * The {@link SpringApplication#run(Class, String...)} method is invoked to launch the application.
 * </p>
 */
@SpringBootApplication
@EnableScheduling
public class PkHotelBackendApplication {

    /**
     * The main method that runs the PK Hotel Backend application.
     *
     * @param args the command-line arguments passed to the application.
     * @see SpringApplication#run(Class, String...)
     */
    public static void main(String[] args) {
        SpringApplication.run(PkHotelBackendApplication.class, args);
    }

}
