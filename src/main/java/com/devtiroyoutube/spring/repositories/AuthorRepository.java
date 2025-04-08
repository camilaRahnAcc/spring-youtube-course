package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {
    //JPA understand this method name
    Iterable<Author> ageLessThan(int age);

    //JPA didn't understand this method name
    @Query("SELECT a from Author a where a.age>?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int i);
}
