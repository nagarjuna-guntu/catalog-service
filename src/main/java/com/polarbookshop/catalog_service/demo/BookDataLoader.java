package com.polarbookshop.catalog_service.demo;

import java.util.List;
import com.polarbookshop.catalog_service.domain.Publisher;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookRepository;

@Component
@Profile("testdata") // use @ConditionalOnProperty(name = "polar.testdata.enabled", havingValue = "true") instead
public class BookDataLoader {
	
	private final BookRepository bookRepository;
	
	public BookDataLoader(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadBookDataForTest() {
		
		bookRepository.deleteAll();
		
		 var book1 = Book.of(
				 "1234567891", 
				 "Northern Lights",
			     "Lyra Silverstar", 
			     9.90, null);
		 var book2 = Book.of(
				 "1234567892", 
				 "Polar Journey",
			     "Iorek Polarson", 
			     12.90, Publisher.Polar.getName());
		 var book3 = Book.of(
				 "1491910771", 
				 "Head First Java: A Brain-Friendly Guide",
			     "Kathy Sierra", 
			     9.90, Publisher.O_Reilly.getName());
		 var book4 = Book.of(
				 "0134685997", 
				 "Effective Java 3rd Edition",
			     "Joshua Bloch", 
			     59.99, Publisher.Addison_Wesley.getName());
		 
		 var book5 = Book.of( 
				 "0135404541",
		         "Core Java for the Impatient 4th Edition",
		          "Cay Horstmann",
		          71.99, Publisher.Addison_Wesley.getName());
		 var book6 = Book.of(
				 "1617294543",
				 "Microservices Patterns: With examples in Java",
				 "Chris Richardson",
				 59.12, Publisher.Manning.getName());
		 var book7 = Book.of(
				 "1492076988",
				 "Spring Boot: Up and Running: Building Cloud Native Java and Kotlin Applications",
				 "Mark Heckler",
				 65.12, Publisher.O_Reilly.getName());
		 var book8 = Book.of(
				 "1633437973",
				 "Spring Security in Action, Second Edition",
				 "Laurentiu Spilca ",
				 47.4, Publisher.Manning.getName());

		 bookRepository.saveAll(List.of(
				 book1, book2, book3, 
				 book4, book5, book6, 
				 book7, book8));
		 
	}

}
