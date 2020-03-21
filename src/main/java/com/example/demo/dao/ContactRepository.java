package com.example.demo.dao;

import com.example.demo.entity.User;

public interface ContactRepository {

	void someContactMethod(User user);

	User anotherContactMethod(User user);
}
