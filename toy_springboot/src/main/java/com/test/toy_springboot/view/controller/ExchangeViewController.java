package com.test.toy_springboot.view.controller;

import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.category.service.CategoryService;
import com.test.toy_springboot.photo.service.PhotoService;
import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.service.ShopService;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/exchange")
public class ExchangeViewController {

    private UserService userService;
    private ShopService shopService;
    private ToyService toyService;
    private PhotoService photoService;
    private CategoryService categoryService;

    @Autowired
    public ExchangeViewController(UserService userService, ShopService shopService, ToyService toyService, CategoryService categoryService) {
        this.shopService = shopService;
        this.toyService = toyService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String exchange(Model model,  HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return "redirect:signIn";
        }
        String userId = currentUserID; // 토큰으로 Id 읽어오기
        User user = userService.getUserById(currentUserID);
        model.addAttribute("myShopID", user.getShop().getShop_id());
        model.addAttribute("currentUser", user);
        return "exchange";
    }
}