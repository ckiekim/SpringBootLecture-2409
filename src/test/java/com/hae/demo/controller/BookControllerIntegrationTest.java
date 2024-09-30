package com.hae.demo.controller;

import com.hae.demo.entity.Book;
import com.hae.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc       // MockMvc를 자동으로 구성하여 웹 요청을 테스트
public class BookControllerIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private BookService bookService;

    @BeforeEach
    void setup() { }    // 필요시 추가

    @Test
    void testList() throws Exception {
        // Given
        List<Book> bookList = new ArrayList<>();
        bookList.add(Book.builder().bid(1).title("title 1").price(10000).build());
        bookList.add(Book.builder().bid(2).title("title 2").price(20000).build());
        when(bookService.getBookList()).thenReturn(bookList);

        // When & Then - /book/list 엔드포인트에 대한 GET 요청 테스트
        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bookList"))
                .andExpect(view().name("book/list"));
    }

    @Test
    void testInsertForm() throws Exception {
        mockMvc.perform(get("/book/insert"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/insert"));
    }

    @Test
    void testInsertProc() throws Exception {
        mockMvc.perform(post("/book/insert")
                    .param("bid", "0").param("title","title").param("price","10000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/list"));
        verify(bookService, times(1)).insertBook(any(Book.class));
    }

    @Test
    void testUpdateForm() throws Exception {
        Book bookToUpdate = Book.builder().bid(4).title("title 4").price(24000).build();
        when(bookService.getBook(4)).thenReturn(bookToUpdate);
        mockMvc.perform(get("/book/update/4"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/update"));
    }

    @Test
    void testUpdateProc() throws Exception {
        Book updatedBook = Book.builder().bid(4).title("title 4").price(24000).build();
        mockMvc.perform(post("/book/update")
                    .param("id","4").param("title", "new title"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/list"));
        verify(bookService, times(1)).updateBook(any(Book.class));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(get("/book/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/list"));
        verify(bookService, times(1)).deleteBook(1);
    }
}
