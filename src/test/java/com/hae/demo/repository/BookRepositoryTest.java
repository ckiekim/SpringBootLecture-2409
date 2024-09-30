package com.hae.demo.repository;

import com.hae.demo.entity.Book;
import com.hae.demo.repositoty.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 테스트마다 트랜잭션이 생성되고, 테스트가 끝나면 자동으로 롤백
@DataJpaTest
public class BookRepositoryTest {
    @Autowired private BookRepository bookRepository;

    @Test
    void testSaveAndFindBook() {
        // Given
        Book book = new Book(0, "title", "author", "company", 20000, "Image URL", "summary");

        // When
        bookRepository.save(book);

        // Then
        List<Book> bookList = bookRepository.findAll();
        int size = bookList.size();
        System.out.println("size = " + size);

        assertThat(bookList).hasSize(1);
        assertThat(bookList.get(0).getTitle()).isEqualTo("title");
        assertThat(bookList.get(0).getPrice()).isEqualTo(20000);
    }

    @Test
    void testSaveAndFindBookByCompany() {
        // Given
        Book book = new Book(0, "title", "author", "company", 20000, "Image URL", "summary");

        // When
        bookRepository.save(book);

        // Then
        List<Book> bookList = bookRepository.findByCompany("company");
        assertThat(bookList).hasSize(1);
        assertThat(bookList.get(0).getCompany()).isEqualTo("company");
    }
}
