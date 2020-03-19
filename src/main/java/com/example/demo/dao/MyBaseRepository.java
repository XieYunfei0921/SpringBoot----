package com.example.demo.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * 如果不想对中间接口创建Spring Bean的时候,使用注解@NoRepositoryBean
 */
@NoRepositoryBean
public interface MyBaseRepository<T,ID> extends Repository<T,ID> {
	Optional<T> findByName(ID id);

	<S extends T> S save(S entity);
}
