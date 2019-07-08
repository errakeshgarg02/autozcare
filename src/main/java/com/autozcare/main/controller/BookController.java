package com.autozcare.main.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autozcare.main.model.Book;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BookController {
	
	//https://www.mkyong.com/spring-boot/spring-rest-spring-security-example/
	
	@GetMapping(path = {"/books"}, produces = {"application/json"})
	public List<Book> getAllBooks() {
		log.debug("entered getAllBooks method");
		Book book = new Book();
		book.setId(UUID.randomUUID().toString());
		book.setName("Mathematics");
		return Arrays.asList(book, book);
	}

}
