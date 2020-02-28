package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @SpringBootApplication =@EnableAutoConfiguration + @ComponentScan
 * + @Configuration
 * 扫描注解`@ComponentScan` 扫描的是应用所属的包
 * @Configuration 为额外的配置包
 * */
/**
 * `spring-boot-devtools`的使用，包含应用快速启动的支持。
 * 1. 默认属性
 * springboot使用缓存提高库的支持效率。例如,`template engines`缓存可以避免重复的转换临时文件.
 * 缓存模式在生产模式下非常有效,可能会阻碍你对应用的改变,所以默认情况下是关闭的.
 * 缓存属性可以在`application.properties`文件中设置,提高`spring.thymeleaf.cache`属性.
 * 不需要手动配置其他设置,`spring-boot-devtools`自动应用敏感配置.
 * 如果你需要获取更多开发的的信息,这个工具可以为web提供DEBUG功能,给出到来的请求信息.如果你需要打印请求信息,
 * 可以设置`spring.http.log-request-details`属性
 *
 * 2. 自动重启
 * 当文件的类路径发生改变的时候，会自动重启。使用IDE时很方便,给予代码修改的最快响应.默认情况下会监听类路径的改变.
 * 自动重启在热重载的情况下是非常有效的,
 * + 改变条件评估的日志记录
 * 默认情况下,每次重启的时候,都会记录状态变化评估,可以屏蔽记录的出现
 * + 配置不需要参加重启的资源
 * 一些资源重启的时候,即使发生了变化也不会触发重启,所以不需要对其进行修改.例如
 * --  /META-INF/maven
 * --  /META-INF/resources
 * --  /resources
 * --  /static
 * --  /public
 * --  /templates
 *
 * 3. 观测额外路径
 * 对非类路径的文件进行修改时,如果你希望它进行重启,你需要设置`spring.devtools.restart.additional-paths`
 * 配置额外的监控目录.可以结合`spring.devtools.restart.exclude`控制重启内容.
 *
 * 4. 关闭重启功能
 * 可以在`application.properties`中配置`spring.devtools.restart.enabled`重启属性.
 * 可以在SpringApplication.run(…​)前设置关闭重启
 *
 * 5. 使用触发文件
 * 当你使用IDE进行编程的时候，可以设置触发文件触发重启动作。
 * 使用`spring.devtools.restart.trigger-file`指定触发文件,触发文件必选处于类路径中
 * 文本结构如下:
 * src
 * +- main
 *     +- resources
 *         +- .reloadtrigger
 * 这样指定
 * spring.devtools.restart.trigger-file=.reloadtrigger
 *
 * 6. 自定义重启类加载器
 *
 *
 * */

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
//		System.setProperty("spring.devtools.restart.enabled","false"); // 关闭重启功能
		SpringApplication.run(Application.class,args);
	}
}
