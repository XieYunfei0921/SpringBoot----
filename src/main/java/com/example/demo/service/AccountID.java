package com.example.demo.service;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AccountID {
	private String name;

	private Double money;
}
