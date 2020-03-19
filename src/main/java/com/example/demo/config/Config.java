package com.example.demo.config;

import com.example.demo.config.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories // 开启JPA接口的代理实例注解
//@EnableRedisRepositories 开启Redis接口代理实例注解
public class Config {
	/*
	*   将所有注解配置到此注解类,使用@Import 注解导入这个注解类
	* */
	String NAME="Sandee";
}
