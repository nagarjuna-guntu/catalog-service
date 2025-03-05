package com.polarbookshop.catalog_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.polarbookshop.catalog_service.config.MyTestContainers;

@SpringBootTest
@Testcontainers
@ImportTestcontainers(MyTestContainers.class)
class CatalogServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
