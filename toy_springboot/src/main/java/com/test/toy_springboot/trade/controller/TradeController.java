package com.test.toy_springboot.trade.controller;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.trade.domain.Trade;
import com.test.toy_springboot.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeService tradeService;
    @Autowired
    public TradeController(TradeService tradeService){
        this.tradeService = tradeService;
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getTradeList(){
        List<Trade> tradeList = tradeService.getTradeList();
        if(tradeList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(tradeList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trade> saveTradeWithoutUser(@RequestBody Trade trade) {
        Trade resultTrade = tradeService.addTradeWithoutUser(trade);
        if(resultTrade == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultTrade, HttpStatus.OK);
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
