package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository3 extends CrudRepository<User,Long>, CustomizedUserRepository {
	// 声明查询方法

}
