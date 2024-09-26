package com.hae.demo.controller;

import com.hae.demo.entity.User;
import com.hae.demo.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerProc(String uid, String pwd, String pwd2, String uname, String email) {
        if (userService.getUserByUid(uid) == null && pwd.equals(pwd2) && pwd.length() >= 4) {
            String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            User user = User.builder()
                    .uid(uid).pwd(hashedPwd).uname(uname).email(email).regDate(LocalDate.now())
                    .build();
            userService.registerUser(user);
        }
        return "redirect:/user/list";
    }

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
