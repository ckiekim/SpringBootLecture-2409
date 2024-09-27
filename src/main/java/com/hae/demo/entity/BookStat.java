package com.hae.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookStat {
    private int bid;
    private String title;
    private String company;
    private int unitPrice;
    private int quantity;
    private int totalPrice;         // unitPrice * quantity
}
