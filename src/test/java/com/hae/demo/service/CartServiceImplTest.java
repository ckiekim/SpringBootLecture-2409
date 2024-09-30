package com.hae.demo.service;

import com.hae.demo.entity.Book;
import com.hae.demo.entity.Cart;
import com.hae.demo.entity.User;
import com.hae.demo.repositoty.BookRepository;
import com.hae.demo.repositoty.CartRepository;
import com.hae.demo.repositoty.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {
    @Mock private UserRepository userRepository;
    @Mock private BookRepository bookRepository;
    @Mock private CartRepository cartRepository;
    @InjectMocks private CartServiceImpl cartService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartListByUser() {
        // Given - 특정 사용자의 장바구니 목록을 설정
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        when(cartRepository.findByUserUid("user")).thenReturn(Arrays.asList(cart1, cart2));

        // When
        List<Cart> cartList = cartService.getCartListByUser("user");

        // Then
        assertThat(cartList).hasSize(2);
    }

    @Test
    void testAddToCart_UserExists() {
        // Given - 유저와 책이 존재하는 경우
        User user = new User();
        Book book = new Book();
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        // When - addToCart 메서드 호출
        cartService.addToCart("user1", book, 2);

        // Then - cartRepository의 save 메서드가 호출되었는지 검증
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testAddToCart_UserNotFound() {
        // Given - 유저가 존재하지 않는 경우
        when(userRepository.findById("user2")).thenReturn(Optional.empty());

        // When & Then - 예외가 발생하는지 검증
        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.addToCart("user2", new Book(), 2);
        });

        assertThat(exception.getMessage()).isEqualTo("user not found");
    }

    @Test
    void testClearCart() {
        // Given - 사용자의 장바구니 목록을 설정
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        when(cartRepository.findByUserUid("user3")).thenReturn(Arrays.asList(cart1, cart2));

        // When - clearCart 메서드 호출
        cartService.clearCart("user3");

        // Then: cartRepository의 deleteAll 메서드가 호출되었는지 검증
        verify(cartRepository, times(1)).deleteAll(Arrays.asList(cart1, cart2));
    }

    @Test
    void testRemoveFromCart() {
        // When - removeFromCart 메서드 호출
        cartService.removeFromCart(1);

        // Then - cartRepository의 deleteById 메서드가 호출되었는지 검증
        verify(cartRepository, times(1)).deleteById(1);
    }
}
