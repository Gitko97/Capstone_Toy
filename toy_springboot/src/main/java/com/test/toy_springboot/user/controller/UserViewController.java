package com.test.toy_springboot.user.controller;

import com.test.toy_springboot.user.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserViewController {
    @GetMapping("/signIn")
    public String loginDto(Model model){
        return "signIn";
    }


    @GetMapping("/signUp")
    public String signUpDto(Model model){
        return "signUp";
    }
}

