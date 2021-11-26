package com.test.toy_springboot.trade.controller;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
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
    private final ToyService toyService;
    @Autowired
    public TradeController(TradeService tradeService, ToyService toyService){
        this.toyService = toyService;
        this.tradeService = tradeService;
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
            toyService.toySetTradeComplete(toy.getToy_id());
        }
        for(Toy toy : resultTrade.getFrom_toy()){
            toyService.toySetTradeComplete(toy.getToy_id());
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
            toyService.toySetTradeTrade(toy.getToy_id());
        }
        for(Toy toy : resultTrade.getFrom_toy()){
            toyService.toySetTradeTrade(toy.getToy_id());
        }
        resultTrade.setTrade_status(2);
        tradeService.updateTrade(resultTrade);
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
