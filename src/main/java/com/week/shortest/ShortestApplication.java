package com.week.shortest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShortestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortestApplication.class, args);
    }

}
