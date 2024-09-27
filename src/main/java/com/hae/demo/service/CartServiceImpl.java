package com.hae.demo.service;

import com.hae.demo.entity.Book;
import com.hae.demo.entity.Cart;
import com.hae.demo.entity.User;
import com.hae.demo.repositoty.BookRepository;
import com.hae.demo.repositoty.CartRepository;
import com.hae.demo.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private CartRepository cartRepository;

    @Override
    public List<Cart> getCartListByUser(String uid) {
        return cartRepository.findByUserUid(uid);
    }

    @Override
    public void addToCart(String uid, Book book, int quantity) {
        Optional<User> user = userRepository.findById(uid);
        if (user.isPresent()) {
            Cart cart = Cart.builder()
                    .user(user.get()).book(book).quantity(quantity)
                    .build();
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("user not found");
        }
    }

    @Override
    public void clearCart(String uid) {
        List<Cart> userCartList = cartRepository.findByUserUid(uid);
        cartRepository.deleteAll(userCartList);
    }

    @Override
    public void removeFromCart(int cid) {
        cartRepository.deleteById(cid);
    }
}
