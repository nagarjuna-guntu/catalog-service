package com.polarbookshop.catalog_service.web;


import org.springframework.http.HttpStatus;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("books")
@Slf4j
public class BookController {
	
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping
	public Iterable<Book> get() {
		log.info("Fetching the list of books in the catalog");
		return bookService.viewBooks();		
	}
	
	@GetMapping("{isbn}")
	public Book getByIsbn(@PathVariable String isbn) {
		log.info("Fetching the book with ISBN {} from the catalog", isbn);
		return bookService.viewBookDetails(isbn);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Book create(@Valid @RequestBody Book book) {
		log.info("Adding a new book to the catalog with ISBN {}", book.isbn());
		return bookService.addBook(book);
	}
	
	@DeleteMapping("{isbn}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteByIsbn(@PathVariable String isbn) {
		log.info("Deleting a book from the catalog with ISBN {}", isbn);
		bookService.removeBook(isbn);
	}
	
	@PutMapping("{isbn}")
	public Book update(@PathVariable String isbn, @Valid @RequestBody Book book) {
		log.info("Updating an exsting book to the catalog with ISBN {}", book.isbn());
		return bookService.editBookDetails(isbn, book);
	}
 
}
