package com.example.demo.dao;

import com.example.demo.CustomizedSave;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository5 extends CrudRepository<User,Long>, CustomizedSave<User> {
}
