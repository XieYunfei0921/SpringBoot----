package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseAccountService implements AccountService{
	private AccountID accountId;
	/**
	 *  注意如果类只有一个构造器，可以省略注解`@Autowired`
	 * */
	@Autowired
	public DatabaseAccountService(AccountID account){
		this.accountId=account;
	}
}
