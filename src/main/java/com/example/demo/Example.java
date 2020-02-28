package com.example.demo;

import com.example.demo.config.Config;
import com.example.demo.config.DataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建Jar包
 * 使用`spring-boot-maven-plugin`进行打包,在pom文件中添加如下依赖
 * {{{
*     <build>
 *     <plugins>
 *         <plugin>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-maven-plugin</artifactId>
 *         </plugin>
 *     </plugins>
 *   </build>
 * }}}
 *
 * */
/**
 * 配置自动配置
 * 使用@EnableAutoConfiguration 或者@SpringBootApplication 注解添加到@Configration中
 *
 * 使用`--debug`可以查看使用了什么配置
 * */
/*
* RestController    模板注解,它是控制器注解@Controller
* RequestMapping    提供信息路由的功能,任何HTTP请求,"/"都会被发送到`home`方法中
* EnableAutoConfiguration   允许自动配置注解,告知springBoot 去猜你想要配置spring的属性,依据与你添加的jar依赖
*   由于spring-boot-starter-web添加了Tomcat和Spring MVC 所以你可以自动配置web应用
* SpringBootApplication 使用JPA程序时,这个注解用于搜索`@Entity`注解
*  下面是注解内部信息
*  {{{
*       @AliasFor(
        annotation = EnableAutoConfiguration.class
	    )
	    Class<?>[] exclude() default {};

	    @AliasFor(
	        annotation = EnableAutoConfiguration.class
	    )
	    String[] excludeName() default {};

	    @AliasFor(
	        annotation = ComponentScan.class,
	        attribute = "basePackages"
	    )
	    String[] scanBasePackages() default {};

	    @AliasFor(
	        annotation = ComponentScan.class,
	        attribute = "basePackageClasses"
	    )
	    Class<?>[] scanBasePackageClasses() default {};

	    @AliasFor(
	        annotation = Configuration.class
	    )
	    boolean proxyBeanMethods() default true;
*  }}}
*  可以看出其包含了自动配置注解@EnableAutoConfiguration，包扫描注解@ComponentScan
*   所以与@EnableAutoConfiguration 连用会导致报错
*
 * */
@Configuration
@Import({Config.class,DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
@RestController
public class Example {

	@RequestMapping("/")
	String home(){
		return "hello world";
	}

	public static void main(String[] args) {
		SpringApplication.run(Example.class,args);
	}
}
