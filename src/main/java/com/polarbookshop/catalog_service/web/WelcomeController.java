package com.polarbookshop.catalog_service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polarbookshop.catalog_service.config.PolarProperties;

@RestController
public class WelcomeController {
	
	private final PolarProperties polarProperties;
	
	public WelcomeController(PolarProperties polarProperties) {
		this.polarProperties = polarProperties;
	}
	
	@GetMapping("/welcome")
	String welcome() {
		return polarProperties.greeting();
	}

}
