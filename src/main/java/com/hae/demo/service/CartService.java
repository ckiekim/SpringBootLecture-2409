package com.hae.demo.service;

import com.hae.demo.entity.Book;
import com.hae.demo.entity.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getCartListByUser(String uid);

    void addToCart(String uid, Book book, int quantity);

    void clearCart(String uid);

    void removeFromCart(int cid);

}
