package com.test.toy_springboot.shop.service;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.repository.ShopRepository;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    private ShopRepository dbAccess;

    @Autowired
    public ShopService(ShopRepository dbAccess) {
        this.dbAccess = dbAccess;
    }

    public List<Shop> getShopList(){
        return dbAccess.findAll();
    }

    public Shop addShop(Shop shop){
        return dbAccess.save(shop);
    }

    public Shop getShopById(Long shop_id) throws Exception {
        return dbAccess.findById(shop_id).orElseThrow(() -> new Exception("Shop ID Error"));
    }

    public Shop updateShop(Shop shop) {
        return dbAccess.save(shop);
    }

    public boolean existsByShopId(Long shop_id) {
        return dbAccess.existsById(shop_id);
    }

    public void delete(Shop shop) throws IllegalArgumentException{
        dbAccess.delete(shop);
    }
}
