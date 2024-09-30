package com.hae.demo.entity;

// For JWT
public class AuthenticationRequest {
    private String username;
    private String password;

    // 기본 생성자
    public AuthenticationRequest() {
    }

    // 매개변수 있는 생성자
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getter와 setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
