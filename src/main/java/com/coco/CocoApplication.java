package com.coco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocoApplication.class, args);
	}

}
