web应用开发
---

spring boot时候web开发,可以通过嵌入式的Tomcat,Jetty,Undertow,Netty创建HTTP服务器.
大多数web应用试验`spring-boot-starter-web`模块快速运行.当然可以选择构建响应式web应用,
通过添加`spring-boot-starter-webflux`模块.

1. springMVC 框架
springMVC是基于MVC的web框架,spring MVC可以创建一个`@Controller`和`RestController`
bean,用于处理接受的HTTP请求.控制器中的方法使用注解`@RequestMapping`映射相应的请求.
例如:
```java
@RestController
@RequestMapping(value="/users")
public class MyRestController {

    @RequestMapping(value="/{user}", method=RequestMethod.GET)
    public User getUser(@PathVariable Long user) {
        // ...
    }

    @RequestMapping(value="/{user}/customers", method=RequestMethod.GET)
    List<Customer> getUserCustomers(@PathVariable Long user) {
        // ...
    }

    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long user) {
        // ...
    }

}
```
+ spring MVC的自动配置
spring boot提供对spring MVC的自动配置支持.
spring boot的配置支持如下特征:
    1. 包含`ContentNegotiatingViewResolver`和`BeanNameViewResolver`的bean
    2. 支持静态服务资源,包括对webJar的支持
    3. `Converter`,`GenericConverter`和`Formatter`bean的原子性注册
    4. 支持`HttpMessageConverters`
    5. 支持`MessageCodesResolver`的原子性注册
    6. 支持静态的`index.html`
    7. 支持自定义的`Favicon`
    8. 支持bean`ConfigurableWebBindingInitializer`的原子使用
如果需要位置spring boot MVC自定义,且对MVC进行更多的自定义(例如,拦截器,格式处理器,视图控制器)
等等,可以添加类`@WebMvcConfigurer`中的`@Configuration`类但是不要使用`@EnableWebMvc`
如果希望提供自定义的`@RequestMappingHandlerMapping`,`@RequestMappingHandlerAdapter`
或者`@ExceptionHandlerExceptionResolver`实例,且仍然需要保持MVC的自定义属性,可以使用
`@WebMvcRegistrations`类型声明bean.用于提供这些组件的自定义实例.
如果你希望完全控制spring MVC,可以添加你定义的带有`@EnableWebMvc`的配置`@Configuration`.
或者在@DelegatingWebMvcConfiguration 中添加@`@Configuration-annotated`注解。
作为javadoc描述。

+ HttpMessageConverters
spring MVC使用HTTP消息转换器@HttpMessageConverter,将HTTP请求/响应进行转换.敏感的参数不要放入其中,
例如一个对象可以自动的配置为json形式或者转换为XML形式(都可以使用jackson进行处理),
默认字符串编码为UTF-8.
```java
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.*;

@Configuration(proxyBeanMethods = false)
public class MyConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = ...
        HttpMessageConverter<?> another = ...
        return new HttpMessageConverters(additional, another);
    }

}
```

+ 自定义json的序列化和反序列化
如果使用jackson序列化和反序列化json数据,如果需要写出自己的json序列化器或者反序列化器.
自定义序列化器经常使用json注册,但是spring boot提供`@JsonComponent`注解,使得使用spring boot
注册更加简单.
可以使用`@JsonComponent`实现`JsonSerializer`,`JsonDeserializer`和`KeyDeserializer`实现.
也可以使用在内部类中.
```java
@JsonComponent
public class Example {

    public static class Serializer extends JsonSerializer<SomeObject> {
        // ...
    }

    public static class Deserializer extends JsonDeserializer<SomeObject> {
        // ...
    }

}
```

+ MessageCodesResolver
spring MVC拥有策略，用于生成错误代码@MessageCodesResolver。如果设置属性
`spring.mvc.message-codes-resolver-format`为`PREFIX_ERROR_CODE`或者`POSTFIX_ERROR_CODE`
spring boot会为你创建爱你一个默认消息处理器.

+ 静态内容
默认情况下,spring boot服务于类路径中`/static`目录(或者`/public`,`/resource`,
`/META-INF/resources`)下的静态资源.使用spring MVC中的@ResourceHttpRequestHandler
以便于可以修改添加到`WebMvcConfigurer`中且重新`addResourceHandlers`方法的行为.
独立web应用中.容器的默认服务程序是处于启动状态,如果spring不对其进行处理,将服务于静态资源.
大多数情况下不会发送改变(除非修改了MVC的配置文件),因为spring可以通过@DispatcherServlet
处理请求.
可以设置下面的参数配置静态资源:
```properties
spring.mvc.static-path-pattern=/resources/**
```
也可以使用配置`spring.resources.static-locations`,但是这个的value值是目录名称,默认情况下为`/`
spring boot也使用MVC提供的高级资源处理方式,对于版本中未知的URL判断缓存环的静态资源.
为了使用版本webjar的位置URL,添加`webjars-locator-core`依赖。然后声明webjar。使用jqury作为示例
添加`/webjars/jquery/jquery.min.js`到`/webjars/jquery/x.y.z/jquery.min.js`,其中x.y.z是webjar
版本号.
为了构成缓存环,使用如下配置:
```markdown
spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**
```
动态加载资源的时候,不能够重新命名文件.这就是为什么需要支持合并了,修改后的策略条件静态版本信息到URL
中,且不需要改变文件名称,示例如下:
```markdown
spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**
spring.resources.chain.strategy.fixed.enabled=true
spring.resources.chain.strategy.fixed.paths=/js/lib/
spring.resources.chain.strategy.fixed.version=v12
```

+ 路径匹配和内容协商
springMVC可以映射到了的HTTP请求到处理器上,通过监视请求路径和匹配到应用中定义的映射表中.
例如,`@GetMapping注解或者@Controller 方法`.
springBoot选择取消前缀匹配,意味着`GET /projects/spring-boot.json`不会匹配`@GetMapping("/projects/spring-boot")`
,这样的选择是最好的.这个特征主要用于HTTP客户端,这些客户端不会发送"已接收"的请求头.
需要保证发送正常内容类型到客户端中.现在内容协商更加可靠.
有许多方式处理HTTP客户端与请求头的消息不匹配的处理.不使用前缀匹配,可以使用查询参数,保证请求
`GET /projects/spring-boot?format=json`会被映射到`@GetMapping("/projects/spring-boot")`中
```markdown
spring.mvc.contentnegotiation.favor-parameter=true

# We can change the parameter name, which is "format" by default:
# spring.mvc.contentnegotiation.parameter-name=myparam

# We can also register additional file extensions/media types with:
spring.mvc.contentnegotiation.media-types.markdown=text/markdown
```
前缀匹配当前被弃用,将来的版本可能会删除,如果还是要开启前缀匹配:
```markdown
spring.mvc.contentnegotiation.favor-path-extension=true
spring.mvc.pathmatch.use-suffix-pattern=true
```
使用内容协商要更加的安全
```markdown
spring.mvc.contentnegotiation.favor-path-extension=true
spring.mvc.pathmatch.use-registered-suffix-pattern=true

# You can also register additional file extensions/media types with:
# spring.mvc.contentnegotiation.media-types.adoc=text/asciidoc
```
+ 配置web绑定初始化
spring MVC使用`WebBindingInitializer`初始化特点请求的`WebDataBinder`如果使用
`ConfigurableWebBindingInitializer `创建一个bean,那么spring MVC会自动使其具有
这个特性.

+ 临时模板
和REST服务一样,可以使用Spring MVC去服务动态HTML内容,spring MVC支持多种动态模板.
主要包括Thymeleaf,FreeMarker,JSP等等.spring boot包含自动配置的下述引擎:
    1. FreeMarker
    2. Groovy
    3. Thymeleaf
    4. Mustache

+ 错误处理
默认情况下,spring boot提供错误映射到`/error`中,这个注册的全局错误页面到服务器容器中.对于
机器客户端来说,提供json的错误输出(带有http状态,和异常信息).对于浏览器客户端来说,返回空白的HTML
文件.可以使用@ErrorController的实现,改变默认的错误处理.且注册一个bean或者添加@ErrorAttributes
类型的bean,用于使用当前原理但是将内容替换掉.
也可以使用注解`@ControllerAdvice `自定义json文档,并返回相应的控制器和异常类型.
```java
@ControllerAdvice(basePackageClasses = AcmeController.class)
public class AcmeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(YourException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(new CustomErrorType(status.value(), ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
```

+ 外部映射错误页面
对于不使用Spring MVC的应用,可以使用`ErrorPageRegistrar`接口直接注册错误页,这个抽象可以直接使用
底层嵌入式服务容器进行注册,且可以不需要运行分发服务程序@DispatcherServlet
```markdown
@Bean
public ErrorPageRegistrar errorPageRegistrar(){
    return new MyErrorPageRegistrar();
}


private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
    }
}
```

2. spring webFlux框架
响应式web框架,与MVC不同,不需要服务程序的API(Servlet),异步非阻塞的实现了响应流.
spring webFlux有两个特点:**功能性**和**基于注解配置**,基于注解这一点与MVC相似,如下:
```java
@RestController
@RequestMapping("/users")
public class MyRestController {

    @GetMapping("/{user}")
    public Mono<User> getUser(@PathVariable Long user) {
        // ...
    }

    @GetMapping("/{user}/customers")
    public Flux<Customer> getUserCustomers(@PathVariable Long user) {
        // ...
    }

    @DeleteMapping("/{user}")
    public Mono<User> deleteUser(@PathVariable Long user) {
        // ...
    }
}
```
"WebFlux.fn"是一个功能性变量,用于从实际请求中分离路由配置
```java
@Configuration(proxyBeanMethods = false)
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(GET("/{user}").and(accept(APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/{user}/customers").and(accept(APPLICATION_JSON)), userHandler::getUserCustomers)
                .andRoute(DELETE("/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

}

@Component
public class UserHandler {

    public Mono<ServerResponse> getUser(ServerRequest request) {
        // ...
    }

    public Mono<ServerResponse> getUserCustomers(ServerRequest request) {
        // ...
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // ...
    }
}
```
