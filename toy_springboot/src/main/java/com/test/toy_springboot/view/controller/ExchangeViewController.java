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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String exchange(Model model,  HttpServletRequest request, @RequestParam(required = false) String character, @RequestParam(required = false) String genre) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");


        if(currentUserID == null){
            return "redirect:signIn";
        }
        List<Toy> filteredToyList;
        if (genre != null && character != null){
            filteredToyList = toyService.findToyByGenreAndCharacter(genre, character);
        }else if (genre != null){
            filteredToyList = toyService.findToyByGenre(genre);
        }else if(character != null){
            filteredToyList = toyService.findToyByCharacter(character);
        }else{
            filteredToyList = toyService.getToyList();
        }
        List<Shop> shopList = filteredToyListToShopList(filteredToyList);
        // Debug with print
        /*
        for (Shop shop : shopList){
            System.out.println("Shop ID : " + shop.getShop_id());
            for(Toy toy: shop.getToyList()){
                System.out.println(toy.getToy_id());
            }
        }*/
        String userId = currentUserID; // 토큰으로 Id 읽어오기
        User user = userService.getUserById(currentUserID);
        model.addAttribute("shopList", shopList);
        return "exchange";
    }

    public List<Shop> filteredToyListToShopList(List<Toy> filteredToy){
        Map<Long, Shop> listMap = new HashMap<>();
        for (Toy toy : filteredToy){
            Shop shop = toy.getShop();
            if (!listMap.containsKey(shop.getShop_id())){
                shop.setToyList(new ArrayList<>());
                shop.getToyList().add(toy);
                listMap.put(shop.getShop_id(), shop);
            }else{
                Shop fetchshop = listMap.get(shop.getShop_id());
                fetchshop.getToyList().add(toy);
            }
        }
        List<Shop> resultShop = new ArrayList<>(listMap.values());
        return resultShop;
    }
}