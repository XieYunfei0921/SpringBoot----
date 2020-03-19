package com.example.demo.client;

import com.example.demo.entity.User;
import com.example.demo.dao.UserRepository;

import java.util.List;

/**
 * 注入仓库实例@UserRepository,并使用进行简单的查询
 */
public class JPAClient {
	private final UserRepository userRepository;

	public JPAClient(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	void searchByName(String name){
		List<User> users = userRepository.findByName(name);
	}
}
