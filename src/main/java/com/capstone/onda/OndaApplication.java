package com.capstone.onda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OndaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OndaApplication.class, args);
	}

}

@Configuration
@EnableJpaAuditing
class ApplicationConfig {}
