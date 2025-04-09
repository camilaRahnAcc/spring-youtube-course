package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity,String> {
}
