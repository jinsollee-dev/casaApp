package com.example.casaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CasaAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasaAppApplication.class, args);
    }

}
