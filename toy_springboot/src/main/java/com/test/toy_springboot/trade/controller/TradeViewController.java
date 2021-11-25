package com.test.toy_springboot.trade.controller;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.trade.service.TradeService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping("/trade")
public class TradeViewController {
    private TradeService tradeService;
    private ToyService toyService;
    private UserService userService;

    @Autowired
    public TradeViewController(TradeService tradeService,ToyService toyService, UserService userService) {
        this.toyService = toyService;
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @GetMapping("/{shopId}")
    public String getTradePage(Model model, @PathVariable Long shopId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return "redirect:signIn";
        }
        User user = userService.getUserById(currentUserID);
        model.addAttribute("myShopID", user.getShop().getShop_id());
        model.addAttribute("clickedShopID", shopId);
        return "trade/index";
    }

}
