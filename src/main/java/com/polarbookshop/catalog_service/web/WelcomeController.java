package com.polarbookshop.catalog_service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@GetMapping("/welcome")
	String welcome() {
		return "Welcome to the Catalog-Service!!";
	}

}
