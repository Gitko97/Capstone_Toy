package com.test.toy_springboot.set.controller;

import com.test.toy_springboot.set.domain.Set_goods;
import com.test.toy_springboot.set.service.Set_goods_Service;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SetViewController {

    private UserService userService;
    private ToyService toyService;
    private Set_goods_Service set_goods_Service;
    @Autowired
    public SetViewController(UserService userService, ToyService toyService, Set_goods_Service set_goods_Service) {
        this.toyService = toyService;
        this.userService = userService;
        this.set_goods_Service = set_goods_Service;
    }

    @GetMapping("/setView")
    public String profilePage(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return "redirect:/signIn";
        }
        List<Set_goods> set_goods_list = set_goods_Service.getSetList();
        User user = userService.getUserById(currentUserID);
        model.addAttribute("set_goods_list", set_goods_list);
        model.addAttribute("currentUser", user);
        return "set/index";
    }

}

