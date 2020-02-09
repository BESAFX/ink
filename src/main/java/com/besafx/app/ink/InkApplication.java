package com.besafx.app.ink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InkApplication {

    public static void main(String[] args) {
        SpringApplication.run(InkApplication.class, args);
    }
}
