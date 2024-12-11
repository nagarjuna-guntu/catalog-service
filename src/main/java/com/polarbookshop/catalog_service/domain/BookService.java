package com.polarbookshop.catalog_service.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public Iterable<Book> viewBooks() {
		return bookRepository.findAll();
	}
	
	public Book viewBookDetails(String isbn) {
		return bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookNotFoundException(isbn));
				
	}
	
	public Book addBook(Book book) {
		if (bookRepository.existsByIsbn(book.isbn())) {
			throw new BookAlreadyExistsException(book.isbn());
		}
		
		return bookRepository.save(book);
	}
	
	public void removeBook(String isbn) {
		bookRepository.deleteByIsbn(isbn);
	}
	
	public Book editBookDetails(String isbn, Book book) {
		return bookRepository.findByIsbn(isbn)
				.map(existingBook -> updateBook(existingBook, book))
				.orElseGet(() -> addBook(book));
	}
	
	private Book updateBook(Book existingBook, Book book) {
		var updatedBook = new Book(
				existingBook.isbn(),
				book.title(),
				book.author(),
				book.price());
		return bookRepository.save(updatedBook);
		
	}

}
