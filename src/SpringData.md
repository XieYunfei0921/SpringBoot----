Spring Data
---
+ Spring Data的类型(这里只介绍需要进行演示的)
1. Spring Data JDBC
2. Spring Data JDBC Extension
3. Spring Data JPA
4. Spring Data MongoDB
5. Spring Data Redis
6. Spring Data REST
7. Spring Data For Apache Solr
8. Spring Data For ElasticSearch
---

> Spring Data JPA
0. 综述:
---
这个模块处理基于JPA数据访问层的高级支持.使构建spring程序更加简单,使用了数据访问
层的技术.
应用数据访问层的实现一直是一个麻烦的问题.许多样例代码写到里面,用于执行简单的查询
以及进行分页和编辑的功能.SpringData JPA旨在提升数据访问层的实现效果,主要通过
减小实际需要的数据的消耗.作为一个开发者,你可以写库的接口,包含自定义的查找方法,
Spring会自动提供实现.
特征:
+ 基于Spring和JPA的复杂实现支持
+ 支持Querydsl预测和类型安全的JPA查询
+ 透明化本地类的实现
+ 支持分页,支持动态查询,可以整合数据访问层代码
+ 启动时支持@Query注解
+ 支持实例映射的XML文件
+ 基于库配置的java配置,使用注解@EnableJpaRepositories
---
1. 依赖设置
  在maven项目中,可以在POM文件中这样声明依赖:
  ```xml
  <dependencyManagement>
      <dependencies>
        <dependency>
          <groupId>org.springframework.data</groupId>
          <artifactId>spring-data-releasetrain</artifactId>
          <version>Moore-SR5</version>
          <scope>import</scope>
          <type>pom</type>
        </dependency>
      </dependencies>
  </dependencyManagement>
  ```
当前版本是Moora-SR5版本,版本名称遵循`${name}-${release}`,发行版如下所示:
+ `BUILD-SNAPSHOT`: 当前的版本快照
+ `M1,M2...`: MileStones
+ `RC1,RC2`: 发行版
+ `RELEASE`: GA发行版
+ `SR1,SR2`: 服务发行版
2. 声明一个spring data的依赖
    ```xml
        <dependencies>
          <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
          </dependency>
        <dependencies>
    ```

3. 使用Spring Data库
Spring Data库的目的是减少数据访问(用于持久化到存储设备中)层的代码.
+ 核心概念
 使用本地的类去管理并需要本地类的类型作为参数乐西.这个接口作为标记接口的首要参数.用于
 帮助你发现接口,并继承它.类@CrudRepository 提供了基本的CRUD功能,用于管理的实例类.
 ```java
 public interface CrudRepository<T, ID> extends Repository<T, ID> {

  <S extends T> S save(S entity);      // 保存给定实例

  Optional<T> findById(ID primaryKey); // 返回给定ID对应的实例

  Iterable<T> findAll();               // 返回所有的实例

  long count();                        // 返回实例的数量

  void delete(T entity);               // 删除指定实例

  boolean existsById(ID primaryKey);   // 确定指定ID的实例是否存在

  // … more functionality omitted.
}   
 ```
 上述类的位置为@org.springframework.data.repository.CrudRepository
 其中
    T为仓库管理的domain类的类型
    ID为仓库管理器的ID类型
 + 接口@org.springframework.data.repository.PagingAndSortingRepository用
 于添加简单的实例页数的标记
 ```java
 public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

    Iterable<T> findAll(Sort sort); // 返回所有实例,按照指定规则@sort排序

    Page<T> findAll(Pageable pageable);// 返回满足分页要求@pageable的页
 }
 ```
 查找一个20个实例的页,可以这样使用
 ```markdown
    # 获取bean
    PagingAndSortingRepository<User, Long> repository = // … get access to a bean
    # 查找20个实例,形成一个user页
    Page<User> users = repository.findAll(PageRequest.of(1, 20));
 ```
 除了查询方法之外,查询所派生的计数@count和删除@delete都可以进行
 ```java
 interface UserRepository extends CrudRepository<User, Long> {

    long countByLastname(String lastname); // 按照lastName计数
 }
 ```
 
 + 标准CRUD函数库通常会查询底层数据库,使用SpringData,声明查询分为如下4步
 1. 声明一个接口，继承@Repository或者其子接口，且设置domain类型和ID类型.示例如下:
 ```java
 interface UserResposity extends Repository<User,Long>{}
 ```
 2. 声明查询方法
 ```java
 interface UserResposity extends Repository<User,Long>{
	List<User> findByLastName(String name);
 }
 ```
 3. 创建接口的代理实例,使用@JavaConfig或者XML配置
 + 使用@JavaConfig配置如下
 ```java
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories // 这里可以自定义扫描版,默认为当前注解所在的包
class Config { … }
```
 + 使用JPA的XML文档配置
 ```markdown
 <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
         https://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/data/jpa
         https://www.springframework.org/schema/data/jpa/spring-jpa.xsd">
    
       <jpa:repositories base-package="com.acme.repositories"/>
    
    </beans>
 ```
  JPA命名空间在这个例子中使用,如果使用对其他存储数据块的仓库抽象,需要将其改变为对应
  存储模块的命名空间/换句话说,需要改变jpa去支持例如`mongodb`
  同时,注意到@JavaConfig中没有显示的配置包,因为注解类的包是默认的包.如果需要自定义
  扫描包使用`basePackage`属性指定,形如`Enable${store-name}Repositories`注解.
4. 注入仓库实例，并使用
```java
class SomeClient {

  private final PersonRepository repository;

  SomeClient(PersonRepository repository) {
    this.repository = repository;
  }

  void doSomething() {
    List<Person> persons = repository.findByLastname("Matthews");
  }
}
```

+ 定义仓库接口
1. 仓库@Repository 的定义
典型的,仓库的接口需要继承@Repository,@CrudRepository或者@PagingAndSortingRepository.
另外,除了可以选择继承Spring Data接口,也可以使用@RepositoryDefinition进行仓库接口的注解.
建议选择继承@CrudRepository的方法,可以提供一些了的基本CRUD方法.
2. 选择性的暴露CRUD方法
```java
@NoRepositoryBean
interface MyBaseRepository<T, ID> extends Repository<T, ID> {

  Optional<T> findById(ID id);

  <S extends T> S save(S entity);
}

interface UserRepository extends MyBaseRepository<User, Long> {
  User findByEmailAddress(EmailAddress emailAddress);
}
```

在之前的例子中，定义了一个示例接口，用于所有的domain类。并保留了@findById和@save方法，
这两个方法会被路由到基本仓库的实现中。从而作为存储的选择，因为匹配了@CrudRepository的特征。
所以@UserRepository 现在使用中间接口@MyBaseRepository的功能，可以保存用户信息@sava,且可以
根据ID查找用户,且可以根据地址触发查找.

3. 多个springdata模块使用仓库
  应用中使用唯一的springdata模块使得应用更简单，因为所有仓库接口都被定义在模块中。
  有的时候，应用需要多个应用模块，在这类情况下，仓库定义必须要区别**持久化技术**间的区别。
  当在类路径中发现多个仓库工厂的时候，spring data条目需要严格配置仓库模式。严格配置使用
  仓库上的细节或者domain类去决定spring data模块绑定的仓库定义。
  + 如果使用继承的方式定义仓库，对于指定的spring data模块，是一个合法的候选项。
  + 使用注解的情况下,对于指定的spring data模块，是一个合法的候选项。spring data模块
  接受三方注解(JPA的`@Entity`)或者是提供自身的主机( Spring Data MongoDB 和 
  Spring Data Elasticsearch)
  下面是使用模块指定的方式的仓库定义:
  ```java
  interface MyRepository extends JpaRepository<User, Long> { }
  
  // -------------------------
  @NoRepositoryBean
  interface MyBaseRepository<T, ID> extends JpaRepository<T, ID> { … }

  interface UserRepository extends MyBaseRepository<User, Long> { … }
  ```
  **使用注解的多个仓库定义**
  ```java
    interface PersonRepository extends Repository<Person, Long> { … }
    
    @Entity
    class Person { … }
    
    interface UserRepository extends Repository<User, Long> { … }
    
    @Document
    class User { … }
  ```
  @person使用JPA注解@Entity,所以其属于JPA,@User注解为@Document,属于MongoDB.
  使用domain类,混合使用注解
  ```java
    interface JpaPersonRepository extends Repository<Person, Long> { … }
    
    interface MongoDBPersonRepository extends Repository<Person, Long> { … }
    
    @Entity
    @Document
    class Person { … }
  ```  
  @Person 既可以使用JPA仓库也可以使用MongoDB仓库.
  
  **注解驱动配置包**
  ```java
    @EnableJpaRepositories(basePackages = "com.acme.repositories.jpa")
    @EnableMongoRepositories(basePackages = "com.acme.repositories.mongo")
    class Configuration { … }
  ```
  
4. 定义查询方法
