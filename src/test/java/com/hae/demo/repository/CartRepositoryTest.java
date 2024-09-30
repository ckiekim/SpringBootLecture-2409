package com.hae.demo.repository;

import com.hae.demo.entity.Book;
import com.hae.demo.entity.Cart;
import com.hae.demo.entity.User;
import com.hae.demo.repositoty.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartRepositoryTest {
    @Autowired private CartRepository cartRepository;

    @Test
    void testSaveCart() {
        // Given
        User user = User.builder().uid("brian").uname("브라이언").build();
        Book book = Book.builder().title("title").price(20000).build();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(2);

        // When - Cart save
        Cart savedCart = cartRepository.save(cart);

        // Then
        assertThat(savedCart.getId()).isNotNull();
        assertThat(savedCart.getUser().getUid()).isEqualTo("brian");
        assertThat(savedCart.getBook().getPrice()).isEqualTo(20000);
        assertThat(savedCart.getQuantity()).isEqualTo(2);
    }

    @Test
    void testFindCart() {
        // Given
        User user = User.builder().uid("brian").uname("브라이언").build();
        Book book = Book.builder().title("title").price(20000).build();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(2);
        Cart savedCart = cartRepository.save(cart);

        // When - 저장된 카트 조회
        Cart foundCart = cartRepository.findById(savedCart.getId()).orElse(null);

        // Then
        assertThat(foundCart.getId()).isNotNull();
        assertThat(foundCart.getUser().getUid()).isEqualTo("brian");
        assertThat(foundCart.getBook().getPrice()).isEqualTo(20000);
        assertThat(foundCart.getQuantity()).isEqualTo(2);
    }
}
