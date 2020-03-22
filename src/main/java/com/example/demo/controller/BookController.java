package com.example.demo.controller;

import com.example.demo.dao.BookReposity;
import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
public class BookController {
	@Autowired
	private BookReposity bookReposity;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewBook(@RequestParam String name,@RequestParam String author){
		Book book = new Book(name,author);
		bookReposity.save(book);
		return "Saved";
	}
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Book> getAllUser(){
		return bookReposity.findAll();
	}
}
