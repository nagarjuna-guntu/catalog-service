package com.polarbookshop.catalog_service.web;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.polarbookshop.catalog_service.domain.BookAlreadyExistsException;
import com.polarbookshop.catalog_service.domain.BookNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class BookExceptionHandler {
	
	@ExceptionHandler
	public ProblemDetail handleBookNotFound(BookNotFoundException exception, HttpServletRequest request) {
		var details = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
		details.setType(URI.create(request.getRequestURI()));
		return details;
		
	}
	
	@ExceptionHandler
	public ProblemDetail handleBookAlreadyExists(BookAlreadyExistsException exception, HttpServletRequest request) {
		var details =  ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
		details.setType(URI.create(request.getRequestURI()));
		return details;
	}
	
	@ExceptionHandler
	public ProblemDetail handleValidationErrors(MethodArgumentNotValidException exception, HttpServletRequest request) {
		Map<String, Object> errorsMap = exception.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		ProblemDetail problemDetails = exception.getBody();
		problemDetails.setProperties(errorsMap);
		problemDetails.setStatus(exception.getStatusCode().value());
		problemDetails.setType(URI.create(request.getRequestURI()));
		
		return problemDetails;
		
	}

}
