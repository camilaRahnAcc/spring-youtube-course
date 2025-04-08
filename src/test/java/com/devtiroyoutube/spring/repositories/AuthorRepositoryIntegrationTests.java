package com.devtiroyoutube.spring.repositories;

import com.devtiroyoutube.spring.TestDataUtil;
import com.devtiroyoutube.spring.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> authors = underTest.findAll();
        assertThat(authors)
                .hasSize(3)
                .contains(authorA,authorB,authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        author.setName("Updated");
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        underTest.deleteById(author.getId());
        Optional<Author> result = underTest.findById(author.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

       Iterable<Author> result = underTest.ageLessThan(80);
       assertThat(result).containsExactly(authorB,authorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(80);
        assertThat(result).containsExactly(authorA);
    }
}
