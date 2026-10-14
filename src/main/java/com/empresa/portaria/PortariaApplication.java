package com.empresa.portaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Classe principal: inicia o Spring Boot na porta 8080. */
@SpringBootApplication
public class PortariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortariaApplication.class, args);
    }
}
