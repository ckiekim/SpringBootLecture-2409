package com.hae.demo.repositoty;

import com.hae.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserUid(String uid);
}
