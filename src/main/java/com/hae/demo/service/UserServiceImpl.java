package com.hae.demo.service;

import com.hae.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUid(String uid) {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return List.of();
    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String uid) {

    }

    @Override
    public int login(String uid, String pwd) {
        return 0;
    }
}
