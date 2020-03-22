package com.example.demo.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity // 使用这个注解表示是JPA的实例
public class Customer {
	@Id // 用于标记主键
	@GeneratedValue(strategy = GenerationType.AUTO) // 表示需要自动生成ID
	private Long id;

	private String firstName;
	private String lastName;

	protected Customer(){}

	public Customer(String firstName,String lastName){
		this.firstName=firstName;
		this.lastName=lastName;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}

	public Long getId(){
		return id;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public String getLastName(){
		return this.lastName;
	}
}
