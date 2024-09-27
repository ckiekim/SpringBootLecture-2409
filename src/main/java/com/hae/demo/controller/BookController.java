package com.hae.demo.controller;

import com.hae.demo.entity.Book;
import com.hae.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired private BookService bookService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Book> bookList = bookService.getBookList();
        model.addAttribute("bookList", bookList);
        return "book/list";
    }

    @GetMapping("/insert")
    public String insertForm() {
        return "book/insert";
    }

    @PostMapping("/insert")
    public String insertProc(Book book) {
        bookService.insertBook(book);
        return "redirect:/book/list";
    }

    @GetMapping("/update/{bid}")
    public String updateForm(@PathVariable int bid, Model model) {
        Book book = bookService.getBook(bid);
        model.addAttribute("book", book);
        return "book/update";
    }

    @PostMapping("/update")
    public String updateProc(Book book) {
        bookService.updateBook(book);
        return "redirect:/book/list";
    }

    @GetMapping("/delete/{bid}")
    public String delete(@PathVariable int bid) {
        bookService.deleteBook(bid);
        return "redirect:/book/list";
    }
}
