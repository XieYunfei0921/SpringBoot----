package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository6 extends CrudRepository<User,Long>, QuerydslPredicateExecutor<User> {

}
