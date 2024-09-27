package com.hae.demo.repositoty;

import com.hae.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUid(String uid);

    List<Order> findByOrderDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
