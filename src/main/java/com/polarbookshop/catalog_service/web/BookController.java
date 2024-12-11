package com.polarbookshop.catalog_service.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("books")
public class BookController {
	
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping
	public Iterable<Book> get() {
		return bookService.viewBooks();		
	}
	
	@GetMapping("{isbn}")
	public Book getByIsbn(@PathVariable String isbn) {
		return bookService.viewBookDetails(isbn);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Book create(@Valid @RequestBody Book book) {
		return bookService.addBook(book);
	}
	
	@DeleteMapping("{isbn}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteByIsbn(@PathVariable String isbn) {
		bookService.removeBook(isbn);
	}
	
	@PutMapping("{isbn}")
	public Book update(@PathVariable String isbn, @Valid @RequestBody Book book) {
		return bookService.editBookDetails(isbn, book);
	}
 
}
