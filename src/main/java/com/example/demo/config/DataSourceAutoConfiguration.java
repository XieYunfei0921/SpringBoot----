package com.example.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class DataSourceAutoConfiguration {
	String CONNECTION_NAME="jdbc://127.0.0.1:3306/book";
}
