package com.hae.demo.controller;

import com.hae.demo.entity.Book;
import com.hae.demo.service.BookService;
import com.hae.demo.service.CartService;
import com.hae.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mall")
public class MallController {
    @Autowired private UserService userService;
    @Autowired private BookService bookService;
    @Autowired private CartService cartService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Book> bookList = bookService.getBookList();
        model.addAttribute("bookList", bookList);
        return "mall/list";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam Map<String, String> quantities, HttpSession session, Model model) {
        String uid = (String) session.getAttribute("sessUid");
        System.out.println(uid);
        for (Map.Entry<String, String> entry: quantities.entrySet()) {      // 입력 파라메터의 이름과 갯수를 모를 경우
            String key = entry.getKey();
            String _bid = key.substring(11, key.length() - 1);
            int bid = Integer.parseInt(_bid);
            int quantity = Integer.parseInt(entry.getValue());
            System.out.println(key + ", bid = " + bid + ", quantity = " + quantity);
            if (quantity == 0)
                continue;
            Book book = bookService.getBook(bid);
            cartService.addToCart(uid, book, quantity);
        }

        return "redirect:/mall/list";
    }
}
