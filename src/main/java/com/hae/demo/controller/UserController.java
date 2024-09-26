package com.hae.demo.controller;

import com.hae.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/lombok")
    @ResponseBody
    public String lombok() {
        User user = new User();
        user = new User("james", "1234", "제임스", "james@gmail.com", LocalDate.now());
        user = User.builder()
                .uid("maria").pwd("1234").uname("마리아").email("maria@naver.com").regDate(LocalDate.of(2024,9,1))
                .build();

        return user.toString();
    }
}
