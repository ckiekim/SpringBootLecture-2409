package com.hae.demo.controller;

import com.hae.demo.entity.Cart;
import com.hae.demo.entity.Order;
import com.hae.demo.entity.OrderItem;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<String> orderTitleList = new ArrayList<>();
        for (Order order: orderList) {
            List<OrderItem> orderItemList = order.getOrderItems();
            String title = orderItemList.get(0).getBook().getTitle();
            int size = orderItemList.size();
            if (size > 1)
                title += " 외 " + (size - 1) + " 건";
            orderTitleList.add(title);
        }
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderTitleList", orderTitleList);
        orderTitleList.forEach(item -> System.out.println(item));
        return "order/list";
    }

    @GetMapping("/listAll")
    public String listAll(Model model) {
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        List<Order> orderList = orderService.getOrdersByDateRange(startTime, endTime);
        int totalRevenue = 0, totalBooks = 0;
        List<String> orderTitleList = new ArrayList<>();
        for (Order order: orderList) {
            totalRevenue += order.getTotalAmount();
            int bookCount = 0;
            List<OrderItem> orderItemList = order.getOrderItems();
            for (OrderItem item: orderItemList) {
                bookCount += item.getQuantity();
            }
            totalBooks += bookCount;
            String title = orderItemList.get(0).getBook().getTitle();
            int size = orderItemList.size();
            if (size > 1)
                title += " 외 " + (size - 1) + " 건";
            orderTitleList.add(title);
        }
        model.addAttribute("orderList", orderList);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("orderTitleList", orderTitleList);
        return "order/listAll";
    }

}
