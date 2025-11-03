package com.trackly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class TracklyApplication {

	public static void main(String[] args) {
		System.out.println("DB_HOST = " + System.getenv("DB_HOST"));
		System.out.println("DB_PORT = " + System.getenv("DB_PORT"));
		System.out.println("DB_NAME = " + System.getenv("DB_NAME"));
		System.out.println("DB_USER = " + System.getenv("DB_USER"));
		System.out.println("DB_PASSWORD = " + System.getenv("DB_PASSWORD"));

		SpringApplication.run(TracklyApplication.class, args);
	}
}
