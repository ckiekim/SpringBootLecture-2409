package com.hae.demo.service;

import com.hae.demo.entity.User;
import com.hae.demo.repositoty.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepository;

    @Override
    public User getUserByUid(String uid) {
        return userRepository.findById(uid).orElse(null);       // DB 액세스 결과가 Optional<User>
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String uid) {
        userRepository.deleteById(uid);
    }

    @Override
    public int login(String uid, String pwd) {
        User user = getUserByUid(uid);
        if (user == null)
            return USER_NOT_EXIST;
        if (BCrypt.checkpw(pwd, user.getPwd()))
            return CORRECT_LOGIN;
        return WRONG_PASSWORD;
    }
}
