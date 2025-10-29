package com.example.gymerp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="classpath:custom.properties")
@SpringBootApplication
public class GymErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymErpApplication.class, args);
	}

}
