package com.may.ars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArsApplication.class, args);
    }

}
