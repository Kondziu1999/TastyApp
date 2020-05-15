package com.kondziu.projects.TastyAppBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TastyAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TastyAppBackendApplication.class, args);
	}

}
