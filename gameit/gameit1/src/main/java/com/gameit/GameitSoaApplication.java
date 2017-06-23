package com.gameit;

import com.gameit.config.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableDiscoveryClient
@EnableConfigurationProperties(StorageProperties.class)
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

