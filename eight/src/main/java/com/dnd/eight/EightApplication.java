package com.dnd.eight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class EightApplication {

	public static void main(String[] args) {
		SpringApplication.run(EightApplication.class, args);

	}

}
