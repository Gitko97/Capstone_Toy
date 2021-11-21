package com.test.toy_springboot.view.controller;

import com.test.toy_springboot.photo.service.PhotoService;
import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.service.ShopService;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.toy.domain.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller

public class RegistViewController {

    private ShopService shopService;
    private ToyService toyService;
    private PhotoService photoService;


    @Autowired
    public RegistViewController(ShopService shopService, ToyService toyService) {
        this.shopService = shopService;
        this.toyService = toyService;
    }

    @GetMapping("/upload")
    public String upload(Model model) throws Exception{
        long l = 1;
        Shop shop = shopService.getShopById(l); //현재 접속중인 유저의 shop 읽어오기
        model.addAttribute("currentShop", shop);
        return "regist/upload";
    }

    @GetMapping("/analysis")
    public String analysis(Model model) {

        return "regist/analysis";
    }
}