package com.polarbookshop.catalog_service.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	@BeforeEach
	void setUp() {
		
	}
	

	@Test
	void addBook_Should_Throws_BookAlreadyExistsException_When_The_Book_Exists() {
		var bookIsbn = "1234561232";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, Publisher.Manning.getName());
		when(bookRepository.existsByIsbn(bookIsbn)).thenThrow(BookAlreadyExistsException.class);
		assertThatThrownBy(() -> bookService.addBook(bookToCreate))
			.isInstanceOf(BookAlreadyExistsException.class);
			
	}
	
	@Test
	void viewBooK_Should_Throws_BookNotFoundException_When_The_Book_Not_Exixts() {
		var bookIsbn = "1234561232";
		when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> bookService.viewBookDetails(bookIsbn))
			.isInstanceOf(BookNotFoundException.class)
			.hasMessageContaining("The book not Found with ISBN- ", bookIsbn);
			
	}

}
