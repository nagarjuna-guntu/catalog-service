package com.polarbookshop.catalog_service.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.polarbookshop.catalog_service.config.DataAuditConfig;
import com.polarbookshop.catalog_service.config.MyTestContainers;



@Testcontainers
@DataJdbcTest
@Import(DataAuditConfig.class)
@ImportTestcontainers(MyTestContainers.class)
class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private JdbcAggregateTemplate jdbcAggregateTemplate;
	
	
	@Test
	void findBookByIsbnWhenExisting() {
		var isbn = "1234561237";
		var book = Book.of(isbn, "Title", "Author", 12.90, Publisher.Manning.getName());
		jdbcAggregateTemplate.insert(book);
		Optional<Book> actual = bookRepository.findByIsbn(isbn);
		assertThat(actual).isPresent();
		assertThat(actual.get().isbn()).isEqualTo(isbn);
		
	}
	
	@Test
	void findBookByIsbn_Should_Return_Empty_OPtional_WhenNotExisting() {
		var isbn = "1234561237";
		Optional<Book> actual = bookRepository.findByIsbn(isbn);
		assertThat(actual).isEmpty();
		
	}
	
	 @Test   
	 void whenCreateBookNotAuthenticatedThenNoAuditMetadata() { 
	 var bookToCreate = Book.of("1232343456", "Title", 
	 "Author", 12.90, "Polarsophia"); 
	 var createdBook = bookRepository.save(bookToCreate); 
	 assertThat(createdBook.createdBy()).isNull();     
	 assertThat(createdBook.lastModifiedBy()).isNull(); 
	 }
	 
	 @Test   
	 @WithMockUser("John")
	 void whenCreateBookAuthenticatedThenNoAuditMetadata() { 
	 var bookToCreate = Book.of("1232343456", "Title", 
	 "Author", 12.90, "Polarsophia"); 
	 var createdBook = bookRepository.save(bookToCreate); 
	 assertThat(createdBook.createdBy()).isEqualTo("John");     
	 assertThat(createdBook.lastModifiedBy()).isEqualTo("John");
	 }

}
