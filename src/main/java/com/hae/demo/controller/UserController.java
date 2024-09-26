package com.hae.demo.controller;

import com.hae.demo.entity.User;
import com.hae.demo.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update/{uid}")
    public String updateForm(@PathVariable String uid, Model model) {
        User user = userService.getUserByUid(uid);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update")
    public String updateProc(String uid, String pwd, String pwd2, String uname, String email) {
        User user = userService.getUserByUid(uid);
        if (pwd.equals(pwd2) && pwd.length() >= 4) {
            String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            user.setPwd(hashedPwd);
        }
        user.setUname(uname);
        user.setEmail(email);
        userService.updateUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{uid}")
    public String delete(@PathVariable String uid) {
        userService.deleteUser(uid);
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
