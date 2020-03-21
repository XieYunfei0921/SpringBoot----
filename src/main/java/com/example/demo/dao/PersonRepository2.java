package com.example.demo.dao;

import com.example.demo.CustomizedSave;
import com.example.demo.entity.Person;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

interface PersonRepository2 extends CrudRepository<Person,Long>, CustomizedSave<Person> {
}
