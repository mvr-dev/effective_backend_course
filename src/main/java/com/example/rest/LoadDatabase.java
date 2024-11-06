package com.example.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDataBase(EmployeeRepository repository) {


        return args -> {
            log.info("Preloading "+repository.save(new Employee("Alex", "Director")));
            log.info("Preloading "+ repository.save(new Employee("Ivan", "worker")));
        };
    }
}
