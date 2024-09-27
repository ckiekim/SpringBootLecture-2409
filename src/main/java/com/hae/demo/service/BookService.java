package com.hae.demo.service;

import com.hae.demo.entity.Book;

import java.util.List;

public interface BookService {

    Book getBook(int bid);

    List<Book> getBookList();

    void insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(int bid);

}
