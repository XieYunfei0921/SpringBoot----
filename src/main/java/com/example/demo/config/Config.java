package com.example.demo.config;

import com.example.demo.config.DataSourceAutoConfiguration;
import com.example.demo.dao.MyRepositoryImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories // 开启JPA接口的代理实例注解,默认指向当前包,否则需要自己指定
//@EnableJpaRepositories(repositoryBaseClass= MyRepositoryImpl.class) //定义仓库基础类属性
//@EnableRedisRepositories 开启Redis接口代理实例注解
public class Config {
	/*
	*   将所有注解配置到此注解类,使用@Import 注解导入这个注解类
	* */
	String NAME="Sandee";

	@Bean
	EntityManagerFactory entityManagerFactory(){
		System.out.println("enable manager factory");
		return null;
	}
}
