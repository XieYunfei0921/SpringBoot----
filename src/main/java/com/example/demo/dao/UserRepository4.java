package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository4 extends CrudRepository<User,Long>,HumanRepository,ContactRepository {
	// 这个仓库继承了三个自定义片段和@CrudRepository
}
