package com.hae.demo.service;

import com.hae.demo.entity.Cart;
import com.hae.demo.entity.Order;
import com.hae.demo.entity.OrderItem;
import com.hae.demo.entity.User;
import com.hae.demo.repositoty.BookRepository;
import com.hae.demo.repositoty.CartRepository;
import com.hae.demo.repositoty.OrderRepository;
import com.hae.demo.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private OrderRepository orderRepository;

    @Override
    public Order createOrder(String uid, List<Cart> cartList) {
        User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("user not found"));
        Order order = Order.builder()
                .user(user).orderDateTime(LocalDateTime.now())
                .build();

        int totalAmount = 0;
        for (Cart cart: cartList) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order).book(cart.getBook()).quantity(cart.getQuantity())
                    .price(cart.getBook().getPrice() * cart.getQuantity())
                    .build();
            totalAmount += orderItem.getPrice();
            order.addOrderItem(orderItem);
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        cartRepository.deleteAll(cartList);
        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUser(String uid) {
        return orderRepository.findByUserUid(uid);
    }

    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return orderRepository.findByOrderDateTimeBetween(startTime, endTime);
    }

    @Override
    public void cancelOrder(int oid) {
        orderRepository.deleteById(oid);
    }
}
