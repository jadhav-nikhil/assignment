package com.assignment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AssignmentExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentExampleApplication.class, args);
		log.info("Spring boot Application started ");
	}



}
