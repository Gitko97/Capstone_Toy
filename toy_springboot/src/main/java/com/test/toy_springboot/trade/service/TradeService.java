package com.test.toy_springboot.trade.service;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import com.test.toy_springboot.trade.domain.Trade;
import com.test.toy_springboot.trade.repository.TradeRepository;
import com.test.toy_springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    private TradeRepository dbAccess;
    private ToyService toyService;
    @Autowired
    public TradeService(TradeRepository dbAccess, ToyService toyService) {
        this.toyService = toyService;
        this.dbAccess = dbAccess;
    }

    public List<Trade> getTradeList(){
        return dbAccess.findAll();
    }

    public Trade getTradeById(Long toy_id){
        return dbAccess.getById(toy_id);
    }
    public Trade addTrade(Trade trade){
        return dbAccess.save(trade);
    }

    public Trade addTradeWithoutUser(Trade trade){
        Toy from_toy = toyService.getToyById((trade.getFrom_toy()).get(0).getToy_id());
        Toy to_toy = toyService.getToyById((trade.getTo_toy()).get(0).getToy_id());
        trade.setFrom_user(from_toy.getShop().getUser());
        trade.setTo_user(to_toy.getShop().getUser());
        return dbAccess.save(trade);
    }
    public Trade updateToy(Trade trade) {
        return dbAccess.save(trade);
    }

    public void delete(Trade trade) throws IllegalArgumentException{
        dbAccess.delete(trade);
    }

    public Trade getTradeByFromUser(Long userIndex){
        return dbAccess.findTradeByFromUser(userIndex);
    }

    public Trade getTradeByToUser(Long userIndex){
        return dbAccess.findTradeByToUser(userIndex);
    }

}
