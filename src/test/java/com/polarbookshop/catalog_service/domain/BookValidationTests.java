package com.polarbookshop.catalog_service.domain;


import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class BookValidationTests {
	
	private static Validator validator;
	
	@BeforeAll
	static void setUp() {
	    var factory	= Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	}

	@Test
	void whenAllFieldsCorrectThenValidationSuccess() {
		var book = Book.of("1234567890", "Title", "Author", 9.90, Publisher.Manning.getName());
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertThat(violations).isEmpty();
	}

}
