package com.devtiroyoutube.spring;

import com.devtiroyoutube.spring.domain.Author;
import com.devtiroyoutube.spring.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .name("aaa")
                .age(85)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .name("bbb")
                .age(60)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .name("ccc")
                .age(78)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("24234-234-3")
                .title("The ShadowA")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("56567")
                .title("The ShadowB")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("79879")
                .title("The ShadowC")
                .author(author)
                .build();
    }
}
