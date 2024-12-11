package com.polarbookshop.catalog_service.domain;

public class BookAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public BookAlreadyExistsException(String isbn) {
		super("A book with ISBN- " + isbn + " already exists ");
		
	}
	
	public BookAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	

}
