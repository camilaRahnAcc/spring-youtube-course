package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.TestDataUtil;
import com.devtiroyoutube.spring.domain.entities.AuthorEntity;
import com.devtiroyoutube.spring.domain.entities.BookEntity;
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
public class BookEntityRepositoryIntegrationTests {

    private final BookRepository underTest;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorRepository.save(authorEntity);

        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(authorEntity);
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }


    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorRepository.save(authorEntity);

        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);
        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        Iterable<BookEntity> books = underTest.findAll();
        assertThat(books)
                .hasSize(3)
                .contains(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        authorRepository.save(authorEntity);

        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA(authorEntity);
        underTest.save(bookEntityA);

        bookEntityA.setTitle("Updated");
        underTest.save(bookEntityA);

        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
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
