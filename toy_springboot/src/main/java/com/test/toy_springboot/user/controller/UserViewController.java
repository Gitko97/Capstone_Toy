package com.test.toy_springboot.user.controller;

import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.trade.domain.Trade;
import com.test.toy_springboot.trade.service.TradeService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.dto.LoginDto;
import com.test.toy_springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserViewController {

    private UserService userService;
    private ToyService toyService;
    private TradeService tradeService;
    @Autowired
    public UserViewController(UserService userService, ToyService toyService, TradeService tradeService) {
        this.toyService = toyService;
        this.userService = userService;
        this.tradeService = tradeService;
    }

    @GetMapping("/signIn")
    public String loginDto(Model model){
        return "signIn";
    }


    @GetMapping("/signUp")
    public String signUpDto(Model model){
        return "signUp";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return "redirect:/signIn";
        }
        User user = userService.getUserById(currentUserID);
        List<Trade> fromTrade = tradeService.getTradeByFromUser(user.getUserIndex());
        List<Trade> toTrade = tradeService.getTradeByToUser(user.getUserIndex());
        model.addAttribute("currentUser", user);
        model.addAttribute("fromTrade", fromTrade);
        model.addAttribute("toTrade", toTrade);
        return "user/profile";
    }

}

