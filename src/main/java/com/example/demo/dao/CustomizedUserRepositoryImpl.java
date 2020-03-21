package com.example.demo.dao;

import com.example.demo.entity.User;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
	@Override
	public void someCustomMethod(User user) {
		System.out.println("this is my implement");
	}
}
