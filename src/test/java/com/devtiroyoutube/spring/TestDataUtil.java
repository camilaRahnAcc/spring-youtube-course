package com.devtiroyoutube.spring;

import com.devtiroyoutube.spring.domain.entities.AuthorEntity;
import com.devtiroyoutube.spring.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .name("aaa")
                .age(85)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .name("bbb")
                .age(60)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .name("ccc")
                .age(78)
                .build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("24234-234-3")
                .title("The ShadowA")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("56567")
                .title("The ShadowB")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("79879")
                .title("The ShadowC")
                .authorEntity(authorEntity)
                .build();
    }
}
