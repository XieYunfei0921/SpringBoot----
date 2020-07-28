package com.example.demo.service;

import com.example.demo.dao.BookReposity;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyRestService {

	private final RestTemplate restTemplate;

	public MyRestService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public Person someRestCall(String name){
		return this.restTemplate.getForObject("/{name}/details",Person.class,name);
	}

}
