package com.example.awtjwtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class AwtJwtDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwtJwtDemoApplication.class, args);
    }

}
