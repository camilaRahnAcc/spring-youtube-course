package com.devtiroyoutube.spring.services.impl;

import com.devtiroyoutube.spring.domain.entities.BookEntity;
import com.devtiroyoutube.spring.repositories.BookRepository;
import com.devtiroyoutube.spring.services.BookService;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }
}
