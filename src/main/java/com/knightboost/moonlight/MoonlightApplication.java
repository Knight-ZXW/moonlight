package com.knightboost.moonlight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MoonlightApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonlightApplication.class, args);
	}

}
