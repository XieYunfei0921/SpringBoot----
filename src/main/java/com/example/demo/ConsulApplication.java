package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 使用注解@EnableDiscoveryClient 尝试连接Consul端口`localhost:8500`作为
 * `spring.cloud.consul.host`和`spring.cloud.consul.port`
 */

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class ConsulApplication {

	@RequestMapping("/home")
	public String home(){
		return "hello world";
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsulApplication.class);
	}
}
