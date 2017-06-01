package com.gameit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GameitSoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameitSoaApplication.class, args);
	}

	@Bean
	CommandLineRunner init( ) {
		return (args) -> {
		};
	}

}

