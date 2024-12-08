package edu.zespol5.pkhotelbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PkHotelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PkHotelBackendApplication.class, args);
    }

}
