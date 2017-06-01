package com.gameit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
public class GameitSOAAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameitSOAAuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init( ) {
		return (args) -> {
		};
	}

}

