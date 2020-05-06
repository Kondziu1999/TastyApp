package com.kondziu.projects.TastyAppBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TastyAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TastyAppBackendApplication.class, args);
	}

}
