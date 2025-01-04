package com.polarbookshop.catalog_service.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.polarbookshop.catalog_service.config.SecurityConfig;
import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookNotFoundException;
import com.polarbookshop.catalog_service.domain.BookService;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
@Disabled
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
	
	@Test
	@Disabled
	void getBook_Exists_Should_Return_200_When_Authenticated() {
		var isbn = "7373731394";
        var expectedBook = Book.of(isbn, "Title", "Author", 9.90, "Polarsophia");
        when(bookService.viewBookDetails(isbn)).thenReturn(expectedBook);
        this.mvc
            .get()
            .uri("/books/", isbn)
            .with(jwt())
            .assertThat()
            .hasStatus(HttpStatus.OK);
	}
	
	@Test
	@Disabled
	void getBook_Exists_Should_Return_200_When_Not_Authenticated() {
		var isbn = "7373731394";
        var expectedBook = Book.of(isbn, "Title", "Author", 9.90, "Polarsophia");
        when(bookService.viewBookDetails(isbn)).thenReturn(expectedBook);
        this.mvc
            .get()
            .uri("/books/", isbn)
            .assertThat()
            .hasStatus(HttpStatus.OK);
	}
	
	@Test
	void deleteBook_With_Employee_Role_Should_Return204() {
		var isbn = "7373731394";
		this.mvc
			.delete()
			.uri("/books/", isbn)
			.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_EMPLOYEE")))
			.assertThat()
			.hasStatus(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	void deleteBook_With_Customer_Role_Should_Return403() {
		var isbn = "7373731394";
		this.mvc
			.delete()
			.uri("/books/", isbn)
			.with(jwt().authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
			.assertThat()
			.hasStatus(HttpStatus.FORBIDDEN);
		
	}
	
	@Test
	void deleteBook_With_Unauthentication_Should_Return401() {
		var isbn = "7373731394";
		this.mvc
			.delete()
			.uri("/books/", isbn)
			.assertThat()
			.hasStatus(HttpStatus.UNAUTHORIZED);
		
	}

}
