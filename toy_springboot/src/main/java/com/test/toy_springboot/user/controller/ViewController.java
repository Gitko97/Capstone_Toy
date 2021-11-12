package com.test.toy_springboot.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class ViewController {

    @GetMapping(value = "/", produces="text/plain;charset=UTF-8")
    public String main(){
        return "index";
    }
}