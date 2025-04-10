package com.devtiroyoutube.spring.controllers;


import com.devtiroyoutube.spring.domain.dto.AuthorDto;
import com.devtiroyoutube.spring.domain.entities.AuthorEntity;
import com.devtiroyoutube.spring.mappers.Mapper;
import com.devtiroyoutube.spring.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public List<AuthorDto> listAuthors(){
       List<AuthorEntity> authors= authorService.findAll();

        return authors.stream()
               .map(authorMapper::mapTo)
               .collect(Collectors.toList());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> readOneAuthor(@PathVariable Long id){
        //Option 1
//        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
//
//        return foundAuthor.map(authorEntity -> {
//            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
//            return new ResponseEntity<>(authorDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        //cleaner

        return authorService.findOne(id)
                .map(authorMapper::mapTo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto){

        if(!authorService.isExists(id)){
            return ResponseEntity.notFound().build();
        }

        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        AuthorDto authorDto1 = authorMapper.mapTo(savedAuthorEntity);

        return ResponseEntity.ok(authorDto1);
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto){

        if(!authorService.isExists(id)){
            return ResponseEntity.notFound().build();
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);

        return  new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Long id){
        //still successful even if nothing was deleted.
//        if(!authorService.isExists(id)){
//            return ResponseEntity.notFound().build();
//        }

        authorService.delete(id);

        return ResponseEntity.noContent().build();

    }
}
