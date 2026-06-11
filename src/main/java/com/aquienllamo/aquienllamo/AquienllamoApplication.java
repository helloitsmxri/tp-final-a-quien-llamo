package com.aquienllamo.aquienllamo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties  // lee los valores del application.properties
public class AquienllamoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AquienllamoApplication.class, args);
	}

}
