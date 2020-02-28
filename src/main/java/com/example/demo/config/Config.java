package com.example.demo.config;

import com.example.demo.config.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Config {
	/*
	*   将所有注解配置到此注解类,使用@Import 注解导入这个注解类
	* */
	String NAME="Sandee";
}
