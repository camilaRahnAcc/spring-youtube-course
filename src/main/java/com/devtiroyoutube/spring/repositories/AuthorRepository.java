package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity,Long> {
    //JPA understand this method name
    Iterable<AuthorEntity> ageLessThan(int age);

    //JPA didn't understand this method name
    @Query("SELECT a from AuthorEntity a where a.age>?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int i);
}
