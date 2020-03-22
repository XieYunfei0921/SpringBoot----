package com.example.demo.dao;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

//@RepositoryDefinition(domainClass = Book.class,idClass = Integer.class)
public interface BookReposity extends CrudRepository<Book,Integer> {

}
