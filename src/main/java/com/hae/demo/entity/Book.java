package com.hae.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // auto_increment 속성
    private int bid;
    private String title;
    private String author;
    private String company;
    private int price;
    private String imageUrl;
    @Column(length = 2048)
    private String summary;
}
