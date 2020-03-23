Spring Cloud Consul
---

1. spring cloud consul特征
+ **服务发现**: 实例可以使用Consul代理注册,客户端可以使用spring管理的bean发现实例.
+ **支持Ribbon**: 通过spring cloud netflix对客户端进行负载均衡
+ 支持spring cloud 的负载均衡器(由spring cloud提供的客户端负载均衡)
+ 支持Zuul,通过spring cloud netflix进行动态路由和过滤.
+ **分布式配置**使用Consul的kv存储.

2. 示例程序运行
+ 运行`docker-compose up`
+ 访问`http://localhost:8500`
+ 运行`mvn package`会构建spring cloud的maven仓库
+ 运行jar包`java -jar spring-cloud-consul-sample/target/spring-cloud-consul-sample-${VERSION}.jar`
+ 访问`http://localhost:8080`,验证`{"serviceId":"<yourhost>:8080","host":"<yourhost>","port":8080}`的结果.
+ 运行`java -jar spring-cloud-consul-sample/target/spring-cloud-consul-sample-${VERSION}.jar --server.port=8081`
+ 再次访问`http://localhost:8081`使用`{"serviceId":"<yourhost>:8081","host":"<yourhost>","port":8081}`进行验证.

3. 安装Consul
安装教程: <https://learn.hashicorp.com/consul/getting-started/install.html>

4. consul代理
consul代理客户端必须对于所有的spring cloud consul可用.默认情况下,客户端代理在
`localhost:8500`作为代理.参考代理的文档,用于启动代理客户端,并且确定如何去连接consul
代理类服务器的集群上.在开发情况下,安装consul结束之后,可以使用下述指令启动代理:
```markdown
./src/main/bash/local_run_consul.sh
```

5. 使用consul进行服务发现
服务发现是基于框架的微服务的主要特征,常数处理每个客户端的配置,或者规定的形式很难去处理,或者是
非常脆弱的.consul通过http api和dns提供服务发现的服务.spring cloud consul用于平衡http
api用于服务的注册和发现.
这个不会阻止非springcloud应用的DNS接口平衡问题.(也就是这些应用也会参与平衡操作).consul
代理服务器运行在集群中,可以使用流言传播协议进行交互并使用一致性协议.
+ 激活的方法
使用组@org.springframework.cloud中的@spring-cloud-starter-consul-discovery
激活consul的服务发现.查看spring cloud文档,并使用当前spring cloud版本构建系统.
+ 使用consul注册
如果客户端使用consul注册,提供自己的元数据(比如host,port,id,name,tag等).默认创建
HTTP检查,consul会每个10s方位/health的后台端点.如果健康检查失败,服务的实例会标记为
`critical`.
**示例consul程序**
```java
@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
```
consul的客户端位于`localhost:8500`,这个配置需要定位客户端.
`application.yml`
```yml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
```
> 注意: 使用[spring cloud consul的配置](https://github.com/spring-cloud/spring-cloud-consul/blob/master/docs/src/main/asciidoc/spring-cloud-consul.adoc#spring-cloud-consul-config)
> 获取配置值,需要将配置放在`bootstrap.yml`中,而非是`application.yml`
默认服务名称,实例编号和端口号来自于环境中,这个的名字为`${spring.application.name}`.
spring 上下文ID和服务器端口号`${server.port}`.
为了关闭consul的服务发现,可以设置`spring.cloud.consul.discovery.enabled=false`
可以通过设置`spring.cloud.consul.discovery.register=false`设置注册的关闭.

6. 使用分离的服务注册管理信息
当管理器服务器端口设置的和应用端口不一致的使用,通过设置@management.server.port 属性，
管理服务会被当做单个服务这次而不是应用服务。
例如:
`application.yml`
```yml
spring:
  application:
    name: myApp
management:
  server:
    port: 4452

```
上述配置包含了两个服务
+ 应用服务
```yml
ID: myApp
Name: myApp
```
+ 管理服务
```yml
ID: myApp-management
Name: myApp-management
```

管理服务会继承应用服务的`instanceId`和`serviceName`
例如:
`application.yml`
```yml
spring:
  application:
    name: myApp
management:
  server:
    port: 4452
spring:
  cloud:
    consul:
      discovery:
        instance-id: custom-service-id
        serviceName: myprefix-${spring.application.name}
```
上述配置包含两个服务
+ 应用服务
```yml
ID: custom-service-id
Name: myprefix-myApp
```
+ 管理服务
```yml
ID: custom-service-id-management
Name: myprefix-myApp-management
```
更多的配置可以按照下述配置设置
```markdown
/** Port to register the management service under (defaults to management port) */
spring.cloud.consul.discovery.management-port

/** Suffix to use when registering management service (defaults to "management" */
spring.cloud.consul.discovery.management-suffix

/** Tags to use when registering management service (defaults to "management" */
spring.cloud.consul.discovery.management-tags
```

7. HTTP健康检查
consul实例的健康检查默认在"/health",这个是spring boot Actuator应用的使用的后台.
你需要对其作出改变,甚至改变Actuator应用.consul的时间间隔用于检查健康检查的后端.
分别是10s和1m.
`application.yml`
```yml
spring:
  cloud:
    consul:
      # 配置health检查的时间周期为15s
      discovery: 
        healthCheckPath: ${management.server.servlet.context-path}/health
        healthCheckInterval: 15s
```
可以通过设置`management.health.consul.enabled=false`关闭检查

8. 元数据和consul标签
consul不支持服务的元数据,spring cloud 的服务实例的元数据以@Map<String, String> 
存储，spring cloud consul 使用consul标签去估算元数据，直到consul支持元数据为止。
使用形式`key==value`的标签会分割并使用map的key和value.tage中没有`equal=sign`的会
作为key和value存储.
`application.yml`
```yml
spring:
  cloud:
    consul:
      discovery:
        tags: foo=bar, baz
```
上述产生了一个`foo->bar`和`baz->baz`的map.

9. consul实例ID唯一化
默认情况下,consul实例使用ID注册,这个id等于spring 应用上下文的编号.默认情况下,spring
应用上下文ID使用`${spring.application.name}:comma,separated,profiles:${server.port}`
在大多数情况下,如果需要考虑到id的全局性,使用spring cloud可以提供一个唯一的标识符.
范围为`spring.cloud.consul.discovery.instanceId`.例如:
`application.yml`
```yml
spring:
  cloud:
    consul:
      discovery:
        instanceId: ${spring.application.name}:${vcap.application.instance_id:${spring.application.instance_id:${random.value}}}
```
使用这个元数据,多个部署在本机的服务实例,随机值会会使得这个实例获取唯一性.早spring boot
程序中@vcap.application.instance_id 会被字段装入。这样就不需要随机值。

10. 健康检查请求使用header
header可以用于健康检查的请求，例如，可以尝试注册后台spring cloud config服务器。
`application.yml`
```yml
spring:
  cloud:
    consul:
      discovery:
        # 设置健康检查的header
        health-check-headers:
          X-Config-Token: 6442e58b-d1ea-182e-cfa5-cf9cddef0722
```
通过标准的http请求,header可以使用多个value值
`application.yml`
```yml
spring:
  cloud:
    consul:
      discovery:
        health-check-headers:
          X-Config-Token:
            - "6442e58b-d1ea-182e-cfa5-cf9cddef0722"
            - "Some other value"
```

11. 查找服务
+ 使用负载均衡
spring cloud 支持Feign(REST客户端构建器),且spring RestTemplate会使用逻辑服务名
称/id(不是实际的物理地址)查找服务.无论是Feign还是RestTemplate都支持Ribbon,用于客户
端的负载均衡.
> 使用@RestTemplate 获取服务存储
```java
public class XXX{
    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {
         return new RestTemplate();
    }
}
```
> 使用下述方式进行服务查找(使用逻辑服务名称/id,不要使用全域名)
```java
public class XXX{
	@Autowired
    RestTemplate restTemplate;
    
    public String getFirstProduct() {
       return this.restTemplate.getForObject("https://STORES/products/1", String.class);
    }
}
```
如果你拥有多个数据中心的consul集群,且你希望获取其他数据层的服务,仅仅以服务名称/id是不够的.
这种情况下,需要设置参数@spring.cloud.consul.discovery.datacenters.STORES=dc-west
.这个存储的服务名称/id是STORES,`dc-west`是数据中心(STORES服务存储的位置).
--> 也就是服务属于数据中心.
> 现在的spring cloud也支持负载均衡
在spring cloud ribbon运行的时候,建议使用`@spring.cloud.loadbalancer.ribbon.enabled=false`
,以便于使用阻塞式负载均衡客户端@BlockingLoadBalancerClient，而不是Ribbon的负载均衡器。
+ 使用负载发现的客户端
可以使用`org.springframework.cloud.client.discovery.DiscoveryClient`提供API,
用于服务发现客户端.(没有指定到Netflix的)
```markdown
@Autowired
private DiscoveryClient discoveryClient;

public String serviceUrl() {
    List<ServiceInstance> list = discoveryClient.getInstances("STORES");
    if (list != null && list.size() > 0 ) {
        return list.get(0).getUri();
    }
    return null;
}
```

12. consul的目录监控
consul目录监控利用了consul去监控服务.目录监控有一个阻塞的consul http请求API.用于在
服务改变的时候调用.如果有新的服务数据,那么就会发起一个心跳事件.为了改变配置控制器的频率,
通过改变`spring.cloud.consul.config.discovery.catalog-services-watch-delay`。
默认时间是1000，单位ms，延时是上一个执行代下一次执行的时间间隔。
通过设置`spring.cloud.consul.discovery.catalogServicesWatch.enabled=false.`
关闭服务监控.
监视器使用spring 任务调度器@TaskScheduler 调度consul.默认情况下@ThreadPoolTaskScheduler
池的大小为1.为了改变任务调度器@TaskScheduler,使用常数`@ConsulDiscoveryClientConfiguration.CATALOG_WATCH_TASK_SCHEDULER_NAME`
创建类型为@TaskScheduler 的bean。

13. 使用consul进行分布式配置





