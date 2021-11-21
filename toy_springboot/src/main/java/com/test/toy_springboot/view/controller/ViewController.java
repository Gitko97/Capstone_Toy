package com.test.toy_springboot.view.controller;

import com.test.toy_springboot.category.service.CategoryService;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller

public class ViewController {

    private UserService userService;
    private ToyService toyService;

    @Autowired
    public ViewController(UserService userService, ToyService toyService) {
        this.userService = userService;
        this.toyService = toyService;
    }

    @GetMapping("/home")
    public String main(Model model) throws Exception {
        String userId = "111"; // 토큰으로 Id 읽어오기
        User user = userService.getUserById(userId);
        model.addAttribute("currentUser", user);
        return "home";
    }

}