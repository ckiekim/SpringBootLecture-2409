package com.hae.demo.service;

import com.hae.demo.entity.Cart;
import com.hae.demo.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(String uid, List<Cart> cartList);
    
}
