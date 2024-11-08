package com.example.rest;

import com.example.rest.employees.Employee;
import com.example.rest.employees.EmployeeRepository;
import com.example.rest.orders.Order;
import com.example.rest.orders.OrderRepository;
import com.example.rest.orders.Status;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDataBase(EmployeeRepository repository, OrderRepository orderRepository) {


        return args -> {
            log.info("Preloading "+repository.save(new Employee("Petr","Petrov", "Director")));
            log.info("Preloading "+ repository.save(new Employee("Ivan", "Ivanov","worker")));

            repository.findAll().forEach(employee -> log.info("Preloaded " + employee));


            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
