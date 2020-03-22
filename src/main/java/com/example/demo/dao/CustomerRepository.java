package com.example.demo.dao;

import com.example.demo.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 对实例@Customer的查询
 */
public interface CustomerRepository extends CrudRepository<Customer,Long> {
	List<Customer> findByLastName(String lastName);

	Customer findById(long id);
}
