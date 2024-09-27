package com.hae.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private LocalDateTime orderDateTime;
    private int totalAmount;

    // 연관관계 메소드 추가
    public void addOrderItem(OrderItem orderItem) {
        if (this.orderItems == null)
            this.orderItems = new ArrayList<>();
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
