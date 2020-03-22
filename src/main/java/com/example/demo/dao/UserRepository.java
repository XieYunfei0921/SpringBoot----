package com.example.demo.dao; // 这个类的父类设置启动了非空处理,在package-info.java中

import com.example.demo.entity.User;
import org.springframework.cglib.core.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * 这个接口是@CrudRepository的派生接口,提供计数和删除功能
 */
public interface UserRepository extends CrudRepository<User,String> {

	long countByAge(int age); // 计数函数

	long deleteByName(String name);

	/**
	 * 父类开启了not-null处理
	 * 当查询结果为空的时候,抛出@EmptyResultDataAccessException
	 * 当查询出来的结果含有`null`的时候抛出@IllegalArgumentException
	 */
	User getByAddress(String address);

	/**
	 * 可以接受@address 的空值
	 * 查询结果没有的时候返回空
	 */
	@Nullable
	User findByAddress(@Nullable String address);

	List<User> removeByName(String name);

	List<User> findByName(String name);
	/**
	*  可以查询值为空的查询
	* */
	Optional<User> findByNameAndAddress(String name, String address);

	/**
	 * 使用java.util.Stream 增量式查找,这里使用用户自定义的查找方式
	 * */
	@Query("select u from User u")
	Stream<User> findAllByCustomQueryAndStream();

	Stream<User> readAllByNameNotNull();

	@Query("select u from User u")
	Stream<User> streamAllPaged(Pageable pageable);

	@Async
	Future<User> findByNameAndAge(String name,int age);

//	Page<User> findAll(Pageable pageable);
//
//	Page<User> findAll(Predicate predicate,Pageable pageable);
}
