package com.hae.demo.service;

import com.hae.demo.entity.Cart;
import com.hae.demo.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order createOrder(String uid, List<Cart> cartList);

    List<Order> getOrdersByUser(String uid);

    List<Order> getOrdersByDateRange(LocalDateTime startTime, LocalDateTime endTime);

    void cancelOrder(int oid);

}
