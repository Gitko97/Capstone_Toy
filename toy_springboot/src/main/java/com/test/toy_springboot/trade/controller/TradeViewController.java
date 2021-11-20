package com.test.toy_springboot.trade.controller;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping("/trade")
public class TradeViewController {
    private TradeService tradeService;
    private ToyService toyService;

    @Autowired
    public TradeViewController(TradeService tradeService,ToyService toyService) {
        this.toyService = toyService;
        this.tradeService = tradeService;
    }

    @GetMapping
    public String getTradePage(Model model, Principal principal) throws Exception {
        long l=1;
        long i=2;
        model.addAttribute("myShopID", l);
        model.addAttribute("clickedShopID", i);
        return "trade/index";
    }

}
