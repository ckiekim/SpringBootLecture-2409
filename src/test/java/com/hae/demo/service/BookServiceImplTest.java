package com.hae.demo.service;

import com.hae.demo.entity.Book;
import com.hae.demo.repositoty.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {
    @Mock private BookRepository bookRepository;
    @InjectMocks private BookServiceImpl bookService;   // BookServiceImpl 객체를 생성하고, BookRepository 주입

    @BeforeEach     // 매 테스트 메소드를 실행할 때마다 수행
    void setup() {
        MockitoAnnotations.openMocks(this);     // Mockito 초기화
    }

    @Test
    void testGetBook() {
        // Given
        Book book = Book.builder()
                .bid(101).title("title").price(23000)
                .build();
        when(bookRepository.findById(101)).thenReturn(Optional.of(book));

        // When
        Book foundBook = bookService.getBook(101);

        // Then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("title");
        assertThat(foundBook.getPrice()).isEqualTo(23000);
    }

    @Test
    void testGetBookList() {
        // Given
        Book book1 = Book.builder()
                .bid(101).title("title1").price(23000)
                .build();
        Book book2 = Book.builder()
                .bid(102).title("title2").price(24000)
                .build();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // When
        List<Book> bookList = bookService.getBookList();

        // Then
        assertThat(bookList).hasSize(2);
        assertThat(bookList.get(0).getTitle()).isEqualTo("title1");
        assertThat(bookList.get(1).getPrice()).isEqualTo(24000);
    }

    @Test
    void testInsertBook() {
        Book book = Book.builder()
                .bid(101).title("title").price(23000)
                .build();
        bookService.insertBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBook() {
        Book book = Book.builder()
                .bid(101).title("title").price(23000)
                .build();
        bookService.updateBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDeleteBook() {
        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }
}
