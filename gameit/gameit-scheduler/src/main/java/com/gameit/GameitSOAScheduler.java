package com.gameit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class GameitSOAScheduler {

	public static void main(String[] args) {
		SpringApplication.run(GameitSOAScheduler.class, args);
	}

	@Bean
	CommandLineRunner init( ) {
		return (args) -> {
		};
	}

}

