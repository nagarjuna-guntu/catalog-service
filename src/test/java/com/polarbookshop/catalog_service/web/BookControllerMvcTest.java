package com.polarbookshop.catalog_service.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.polarbookshop.catalog_service.domain.BookNotFoundException;
import com.polarbookshop.catalog_service.domain.BookService;

@WebMvcTest(BookController.class)
class BookControllerMvcTest {
	
	@Autowired
	private MockMvcTester mvc;
	
	@MockitoBean
	private BookService bookService;
	

	@Test
	void whenGetBookNotExistingThenShouldReturn404() throws Exception {
		String isbn = "73737313940";
		when(bookService.viewBookDetails(isbn)).thenThrow(BookNotFoundException.class);
		assertThat(mvc.get().uri("/books/", isbn)).hasStatus(HttpStatus.NOT_FOUND);
	}

}
