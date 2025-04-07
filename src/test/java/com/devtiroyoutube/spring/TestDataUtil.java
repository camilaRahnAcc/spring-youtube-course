package com.devtiroyoutube.spring;

import com.devtiroyoutube.spring.domain.Author;
import com.devtiroyoutube.spring.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("aaa")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("bbb")
                .age(60)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("ccc")
                .age(78)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("24234-234-3")
                .title("The ShadowA")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("56567")
                .title("The ShadowB")
                .authorId(2L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("79879")
                .title("The ShadowC")
                .authorId(3L)
                .build();
    }
}
