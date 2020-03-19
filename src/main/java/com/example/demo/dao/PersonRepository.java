package com.example.demo.dao;

import com.example.demo.entity.Person;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * 使用注解@RepositoryDefinition 进行仓库接口的定义
 */
@RepositoryDefinition(domainClass = Person.class, idClass =Long.class)
public interface PersonRepository {
}
