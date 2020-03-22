package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.demo.dao","com.example.demo.entity"})
public class AccessMysqlDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessMysqlDataApplication.class);
	}
}
