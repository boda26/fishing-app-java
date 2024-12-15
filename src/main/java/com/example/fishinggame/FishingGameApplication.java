package com.example.fishinggame;

import io.github.cdimascio.dotenv.Dotenv;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fishinggame.mapper")
public class FishingGameApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
//		Dotenv dotenv = Dotenv.configure()
//				.directory("/app") // Path where the .env file will be copied
//				.load();

		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		SpringApplication.run(FishingGameApplication.class, args);
	}

}
