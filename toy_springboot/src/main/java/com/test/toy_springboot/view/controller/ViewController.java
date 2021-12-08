package com.test.toy_springboot.view.controller;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class ViewController {

    private UserService userService;
    @Autowired
    public ViewController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String notLoginPage(Model model,  HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID != null){
            return "redirect:/home";
        }
        return "main";
    }

    @GetMapping("/home")
    public String main(Model model,  HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return "redirect:signIn";
        }
        List<Toy> filteredToyList;

        String userId = currentUserID; // 토큰으로 Id 읽어오기
        User user = userService.getUserById(userId);
        Shop shop = user.getShop(); //현재 접속중인 유저의 shop 읽어오기
        model.addAttribute("currentUser", user);
        model.addAttribute("currentShop", shop);
        return "home";
    }

    @GetMapping("/signOut")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
//      return "NotLoginHomePage";
        return "signOut";
    }
}