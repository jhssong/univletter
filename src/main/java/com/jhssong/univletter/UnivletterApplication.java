package com.jhssong.univletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UnivletterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnivletterApplication.class, args);
    }

}
