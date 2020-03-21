package com.example.demo.two;

import com.example.demo.dao.CustomizedUserRepository;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component("specialCustomImpl")
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
	@Override
	public void someCustomMethod(User user) {
		// Your custom implementation

	}

}