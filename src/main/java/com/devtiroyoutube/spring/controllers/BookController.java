package com.devtiroyoutube.spring.controllers;


import com.devtiroyoutube.spring.domain.dto.BookDto;
import com.devtiroyoutube.spring.domain.entities.BookEntity;
import com.devtiroyoutube.spring.mappers.Mapper;
import com.devtiroyoutube.spring.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private BookService bookService;
    private Mapper<BookEntity,BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    //because WE decide the id=isbn and not the system that is why we use PUT instead of POST(system auto generates the id)
    //both for creating and updating
    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        if (bookExists){
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }

    }

    @GetMapping("/books")
    public List<BookDto> listBooks(){
        List<BookEntity> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> readOneBook(@PathVariable String isbn){
        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook
                .map(bookMapper::mapTo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
