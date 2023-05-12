package com.sparta.cloneproject_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloneProjectBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloneProjectBeApplication.class, args);
    }

}
