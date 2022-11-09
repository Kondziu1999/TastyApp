package com.kondziu.projects.TastyAppBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.kondziu.*", "com.agh.*"})
@EnableJpaRepositories()
@EntityScan("com.kondziu.*")
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TastyAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TastyAppBackendApplication.class, args);
	}

}
