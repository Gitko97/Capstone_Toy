package com.test.toy_springboot.trade.controller;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.trade.domain.Trade;
import com.test.toy_springboot.trade.service.TradeService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeService tradeService;
    private final ToyService toyService;
    private final UserService userService;
    @Autowired
    public TradeController(TradeService tradeService, ToyService toyService, UserService userService){
        this.toyService = toyService;
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getTradeList(){
        List<Trade> tradeList = tradeService.getTradeList();
        if(tradeList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(tradeList, HttpStatus.OK);
    }

    @GetMapping("/{trade_id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable Long trade_id){
        Trade trade = tradeService.getTradeById(trade_id);
        if(trade == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trade, HttpStatus.OK);
    }

    @GetMapping("/toTrade")
    public ResponseEntity<List<Trade>> getCurrentUserTradeList(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        String currentUserID = (String) session.getAttribute("currentUserID");
        if(currentUserID == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserById(currentUserID);
        List<Trade> tradeList = tradeService.getStatusTradeByToUser(user.getUserIndex());
        if(tradeList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(tradeList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> saveTradeWithoutUser(@RequestBody Trade trade) {
        Trade resultTrade = tradeService.addTradeWithoutUser(trade);
        if(resultTrade == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(resultTrade.getTrade_id(), HttpStatus.OK);
    }

    @PostMapping("/{trade_id}/complete")
    public ResponseEntity<Long> setTradeComplete(@PathVariable Long trade_id) {
        Trade resultTrade = tradeService.getTradeById(trade_id);
        for(Toy toy : resultTrade.getTo_toy()){
            toyService.updateToyToStatusComplete(toy.getToy_id());
        }
        for(Toy toy : resultTrade.getFrom_toy()){
            toyService.updateToyToStatusComplete(toy.getToy_id());
        }
        resultTrade.setTrade_status(1);
        tradeService.addTrade(resultTrade);
        if(resultTrade == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(resultTrade.getTrade_id(), HttpStatus.OK);
    }

    @PostMapping("/{trade_id}/delete")
    public ResponseEntity<Long> setTradeDelete(@PathVariable Long trade_id) {
        Trade resultTrade = tradeService.getTradeById(trade_id);
        for(Toy toy : resultTrade.getTo_toy()){
            toyService.updateToyToStatusNotTrade(toy.getToy_id());
        }
        for(Toy toy : resultTrade.getFrom_toy()){
            toyService.updateToyToStatusNotTrade(toy.getToy_id());
        }
        tradeService.delete(resultTrade);
        if(resultTrade == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(resultTrade.getTrade_id(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Trade> deleteTrade(@RequestBody Trade trade) {
        try{
            tradeService.delete(trade);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
