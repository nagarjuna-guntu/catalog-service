package com.polarbookshop.catalog_service.domain;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long>{


	Optional<Book> findByIsbn(String isbn);

	boolean existsByIsbn(String isbn);

	@Transactional
	@Modifying
	@Query("delete from Book where isbn = :isbn")
	void deleteByIsbn(String isbn);
		

}
