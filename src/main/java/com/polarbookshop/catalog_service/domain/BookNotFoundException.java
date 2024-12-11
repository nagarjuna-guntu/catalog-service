package com.polarbookshop.catalog_service.domain;

public class BookNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public BookNotFoundException(String isbn) {
		super("The book not Found with ISBN- " + isbn );
	}
	
	public BookNotFoundException(Throwable cause) {
		super(cause);
	}

}
