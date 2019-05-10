package com.sunshine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@ConfigurationProperties
@SpringBootApplication
public class IphttpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IphttpsApplication.class, args);
	}

}
