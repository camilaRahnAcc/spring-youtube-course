package com.devtiroyoutube.spring.services;

import com.devtiroyoutube.spring.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);
}
