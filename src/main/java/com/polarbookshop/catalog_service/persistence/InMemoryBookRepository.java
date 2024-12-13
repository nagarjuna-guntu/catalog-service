package com.polarbookshop.catalog_service.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookRepository;

@Repository
public class InMemoryBookRepository {
	
	private static final Map<String, Book> books =
			new ConcurrentHashMap<>();

	public Iterable<Book> findAll() {
		return books.values();
	}

	public Optional<Book> findByIsbn(String isbn) {
		return books.entrySet().stream()
				.filter(entry -> entry.getKey().equalsIgnoreCase(isbn))
				.map(Map.Entry::getValue)
				.findFirst();
	}

	public boolean existsByIsbn(String isbn) {
		return books.containsKey(isbn);
	}

	public Book save(Book book) {
		books.put(book.isbn(), book);
		return book;
	}

	public void deleteByIsbn(String isbn) {
		books.remove(isbn);
	}

}
