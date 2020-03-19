package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 这个接口是@CrudRepository的派生接口,提供计数和删除功能
 */
public interface UserRepository extends CrudRepository<User,Long> {

	long countByAge(int age); // 计数函数

	long deleteByName(String name);

	List<User> removeByName(String name);

	List<User> findByName(String name);

}
