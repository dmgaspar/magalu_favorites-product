package com.magalu.favorites.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.magalu.favorites.product"}) // force scan JPA entities
public class FavoritesProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoritesProductApplication.class, args);
	}
}
