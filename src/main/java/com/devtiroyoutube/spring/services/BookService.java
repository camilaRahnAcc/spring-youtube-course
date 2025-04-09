package com.devtiroyoutube.spring.services;

import com.devtiroyoutube.spring.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(BookEntity bookEntity);
}
