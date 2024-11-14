package com.example.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RestApplication implements CommandLineRunner {


	private static final Logger log = LoggerFactory.getLogger(RestApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public void run(String... args) throws Exception {
		log.info("Create tables");
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers(" +
				"ID SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
				.stream().map(name -> name.split(" ")).collect(Collectors.toList());
		splitUpNames.forEach(name -> log.info(
				String.format("Inserting customer info for %s %s",name[0],name[1])));
		jdbcTemplate.batchUpdate("INSERT INTO customers (first_name, last_name) VALUES (?,?)",splitUpNames);
		log.info("Querying customer records where 'first_name' is 'Josh':");
		jdbcTemplate.query(
				"SELECT * FROM customers WHERE first_name = ?",
				(rs, numRow) ->
						new Customer(rs.getLong("ID"),rs.getString("first_name"),rs.getString("last_name"))
				,"Josh")
				.forEach(customer -> log.info(customer.toString()));
	}
}
