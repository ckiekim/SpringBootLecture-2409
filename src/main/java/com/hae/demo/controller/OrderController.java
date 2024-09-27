package com.hae.demo.controller;

import com.hae.demo.entity.Cart;
import com.hae.demo.entity.Order;
import com.hae.demo.service.BookService;
import com.hae.demo.service.CartService;
import com.hae.demo.service.OrderService;
import com.hae.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private CartService cartService;
    @Autowired private BookService bookService;

    @GetMapping("/createOrder")
    public String createOrder(HttpSession session) {
        String uid = (String) session.getAttribute("sessUid");
        List<Cart> cartList = cartService.getCartListByUser(uid);
        Order order = orderService.createOrder(uid, cartList);
        return "redirect:/order/list";
    }

    @GetMapping("/list")
    public String list(HttpSession session, Model model) {
        String uid = (String) session.getAttribute("sessUid");
        List<Order> orderList = orderService.getOrdersByUser(uid);
        model.addAttribute("orderList", orderList);
        return "order/list";
    }

}
