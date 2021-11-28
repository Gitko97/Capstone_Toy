package com.test.toy_springboot.view.controller;

import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller

public class ViewController {

    private UserService userService;
    @Autowired
    public ViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String main(Model model,  HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return "redirect:/signIn";
        }
        String userId = currentUserID; // 토큰으로 Id 읽어오기
        User user = userService.getUserById(userId);

        model.addAttribute("currentUser", user);
        return "home";
    }

    @GetMapping("/signOut")
    public String logout(HttpServletRequest request) {
        System.out.println("LOGOUT");
        HttpSession session = request.getSession();
        session.invalidate();
//      return "NotLoginHomePage";
        return "signOut";
    }
}