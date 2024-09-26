package com.hae.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/hello")
    public String hello() {
        return "basic/hello";       // classpath:templates/basic/hello.html
    }

    @ResponseBody
    @GetMapping("/noHTML")
    public String noHtml() {
        return "<h1>Hello Spring Boot!!!</h1>";
    }

    @GetMapping("/redirect")
    public String redirect() {
        return "redirect:/basic/hello";
    }

    @GetMapping("/params")
    public String params(Model model) {     // Web Server -> HTML 에게 정보 전달
        model.addAttribute("name", "제임스");
        return "basic/params";
    }

    @GetMapping("/input")
    public String input(String name, Model model) {     // localhost:8080/basic/input?name=James
        System.out.println(name);
        model.addAttribute("name", name);
        return "basic/params";
    }

}
