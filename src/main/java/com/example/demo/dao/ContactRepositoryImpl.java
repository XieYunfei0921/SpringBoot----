package com.example.demo.dao;

import com.example.demo.entity.User;

public class ContactRepositoryImpl implements ContactRepository {
	@Override
	public void someContactMethod(User user) {
		// 自定义实现
	}

	@Override
	public User anotherContactMethod(User user) {
		// 自定义实现
		return new User();
	}
}
