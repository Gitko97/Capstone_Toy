package com.test.toy_springboot.trade.service;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.trade.domain.Trade;
import com.test.toy_springboot.trade.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    private TradeRepository dbAccess;

    @Autowired
    public TradeService(TradeRepository dbAccess) {
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
