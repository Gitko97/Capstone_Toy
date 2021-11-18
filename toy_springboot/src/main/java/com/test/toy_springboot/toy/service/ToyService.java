package com.test.toy_springboot.toy.service;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.service.ShopService;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {
    private ToyRepository dbAccess;
    private final ShopService shopService;
    @Autowired
    public ToyService(ToyRepository dbAccess, ShopService shopService) {
        this.dbAccess = dbAccess;
        this.shopService = shopService;
    }

    public List<Toy> getToyList(){
        return dbAccess.findAll();
    }

    public Toy getToyById(Long toy_id){
        return dbAccess.getById(toy_id);
    }
    public Toy addToy(Toy toy, Long shop_id) throws Exception {
        Shop mappingShop = shopService.getShopById(shop_id);
        toy.setShop(mappingShop);
        return dbAccess.save(toy);
    }

    public List<Toy> findNotTradeToyWithShopId(Long shop_id){
        return dbAccess.findNotTradeToyWithShopId(shop_id);
    }
    public Toy updateToy(Toy toy) {
        return dbAccess.save(toy);
    }

    public void delete(Toy toy) throws IllegalArgumentException{
        dbAccess.delete(toy);
    }

    public Long findToyBySetId(Long set_id){
        return dbAccess.findToyBySetId(set_id);
    }

    public List<Toy> findToyByGenre(String genre){ return dbAccess.findToyByGenre(genre);}
    public List<Toy> findToyByCharacter(String character){ return dbAccess.findToyByCharacter(character);}
    public List<Toy> findToyByGenreAndCharacter(String genre, String character){ return dbAccess.findToyByGenreAndCharacter(genre, character);}

    public void updateToySetGoods(Long toyId, Long set_id) {
        dbAccess.match_toy_to_setItem(toyId, set_id);
    }
}
