package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.TestDataUtil;
import com.devtiroyoutube.spring.domain.Author;
import com.devtiroyoutube.spring.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private final BookRepository underTest;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);

        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }


    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createTestBookA(author);
        Book bookB = TestDataUtil.createTestBookB(author);
        Book bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        Iterable<Book> books = underTest.findAll();
        assertThat(books)
                .hasSize(3)
                .contains(bookA,bookB,bookC);
    }

    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);

        Book bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);

        bookA.setTitle("Updated");
        underTest.save(bookA);

        Optional<Book> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

//    @Test
//    public void testThatBookCanBeDeleted(){
//        Author author = TestDataUtil.createTestAuthorA();
//        authorDao.create(author);
//
//        Book bookA = TestDataUtil.createTestBookA();
//        bookA.setAuthorId(author.getId());
//        underTest.create(bookA);
//
//        underTest.delete(bookA.getIsbn());
//        Optional<Book> result = underTest.findOne(bookA.getIsbn());
//
//        assertThat(result).isEmpty();
//    }

}
