package com.polarbookshop.catalog_service.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(
		
		@Id
		Long id,
		
		@Version
		int version,
		
		@NotNull(message = "The book ISBN must not be null.")
		@NotBlank(message = "The book ISBN must be defined.")
		@Pattern(regexp = "^([0-9]{10}|[0-9]{13})$",
				 message = "The ISBN format must be valid." )
		String isbn,
		
		@NotBlank(message = "The book title must be defined." ) 
		String title,
		
		@NotBlank(message = "The book author must be defined.")
		String author,
		
		@NotNull(message = "The book price must be defined.") 
		@Positive(message = "The book price must be greater than zero.") 
		double price,
		
		@CreatedDate
		Instant createDate,
		
		@LastModifiedDate
		Instant lastModifiedDate) {
	
	    public static Book of(String isbn, String title, String author, double price) {
	    	return new Book (null, 0, isbn, title, author, price, null, null);
	    }

}
