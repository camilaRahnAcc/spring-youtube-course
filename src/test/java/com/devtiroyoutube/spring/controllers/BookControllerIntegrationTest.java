package com.devtiroyoutube.spring.controllers;

import com.devtiroyoutube.spring.TestDataUtil;
import com.devtiroyoutube.spring.domain.dto.BookDto;
import com.devtiroyoutube.spring.domain.entities.AuthorEntity;
import com.devtiroyoutube.spring.domain.entities.BookEntity;
import com.devtiroyoutube.spring.services.AuthorService;
import com.devtiroyoutube.spring.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;
    private AuthorService authorService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
        this.authorService = authorService;
    }

     @Test
    public void testThatCreateUpdateBookReturnsHttpStatus201Created() throws Exception {
         BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
         BookEntity savedBookEntity = bookService.createBook(
                 testBookEntityA.getIsbn(), testBookEntityA
         );

         BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
         bookDto.setIsbn(savedBookEntity.getIsbn());

         String createBookJson = objectMapper.writeValueAsString(bookDto);

         mockMvc.perform(
                 MockMvcRequestBuilders.put("/books/" + testBookEntityA.getIsbn())
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(createBookJson)
         ).andExpect(
                 MockMvcResultMatchers.status().isOk()
         );

     }

    @Test
    public void testThatCreateBookReturnsCreatedUpdateBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );

    }

    @Test
    public void testThatUpdateBookReturnsUpdateBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createBook(
                testBookEntityA.getIsbn(), testBookEntityA
        );

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setTitle("UPDATED");
        bookDto.setIsbn(testBookEntityA.getIsbn());

        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );

    }

    @Test
    public void testThatListBooksReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }



    @Test
    public void testThatCreateAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorEntityA);

        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(testAuthorEntityA);
        bookService.createBook(testBookEntityA.getIsbn(),testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("24234-234-3")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("The ShadowA")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").value(testAuthorEntityA)
        );
    }

    @Test
    public void testThatGetBooksReturnsHttp200WhenBookExists() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createBook(testBookEntityA.getIsbn(),testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnsHttp404WhenBookNoExists() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
//        bookService.createBook(testBookEntityA.getIsbn(),testBookEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
