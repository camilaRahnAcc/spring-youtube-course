package com.devtiroyoutube.spring.dao;


import com.devtiroyoutube.spring.TestDataUtil;
import com.devtiroyoutube.spring.dao.impl.AuthorDaoImpl;
import com.devtiroyoutube.spring.dao.impl.BookDaoImpl;
import com.devtiroyoutube.spring.domain.Author;
import com.devtiroyoutube.spring.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl authorDao;
    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn,title,author_id) VALUES (?,?,?)"),
                eq("24234-234-3"),eq("The ShadowA"),eq(1L)
        );
    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSql(){
        underTest.findOne("24234-234-3");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn=? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("24234-234-3")
        );
    }

    @Test
    public void testThatFindGeneratesTheCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();
        underTest.update("24234-234-3", book);

        verify(jdbcTemplate).update(
                "UPDATE books SET isbn=?, title=?, author_id=? WHERE isbn= ?",
                "24234-234-3","The ShadowA", 1L,"24234-234-3"
        );

    }

    @Test
    public void testThatDeleteGeneratesCorrectSql(){
      underTest.delete("24234-234-3");

        verify(jdbcTemplate).update(
                "DELETE FROM books WHERE isbn=?",
                "24234-234-3"
        );

    }




}
