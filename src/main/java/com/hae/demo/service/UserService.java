package com.hae.demo.service;

import com.hae.demo.entity.User;

import java.util.List;

public interface UserService {
    public static final int CORRECT_LOGIN  = 0;
    public static final int WRONG_PASSWORD = 1;
    public static final int USER_NOT_EXIST = 2;

    User getUserByUid(String uid);

    List<User> getUserList();

    void registerUser(User user);

    void updateUser(User user);

    void deleteUser(String uid);

    int login(String uid, String pwd);
}
