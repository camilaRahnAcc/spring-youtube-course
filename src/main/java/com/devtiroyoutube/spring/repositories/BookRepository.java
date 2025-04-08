package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book,String> {
}
