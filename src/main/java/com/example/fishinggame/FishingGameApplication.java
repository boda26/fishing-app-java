package com.example.fishinggame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fishinggame.mapper")
public class FishingGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(FishingGameApplication.class, args);
	}

}
