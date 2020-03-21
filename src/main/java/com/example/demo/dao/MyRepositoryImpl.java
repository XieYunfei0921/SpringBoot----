package com.example.demo.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class MyRepositoryImpl<T,ID> extends SimpleJpaRepository<T,ID> {
	public final EntityManager entityManager;

	public MyRepositoryImpl(EntityManager entityManager, JpaEntityInformation entityInformation) {
		super(entityInformation,entityManager);
		this.entityManager=entityManager;
	}

	@Transactional
	public <S extends T> S save(S entity){
		// implements
		return null;
	}
}
