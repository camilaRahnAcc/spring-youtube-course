package com.devtiroyoutube.spring.services.impl;

import com.devtiroyoutube.spring.domain.entities.AuthorEntity;
import com.devtiroyoutube.spring.repositories.AuthorRepository;
import com.devtiroyoutube.spring.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
