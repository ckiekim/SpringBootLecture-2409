package com.hae.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity         // DB table로 사용
@Table(name="customers")    // 테이블 명을 customers 로 명명
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User {
    @Id         // Primary key 지정
    private String uid;
    private String pwd;
    private String uname;
    private String email;
    private LocalDate regDate;
}
