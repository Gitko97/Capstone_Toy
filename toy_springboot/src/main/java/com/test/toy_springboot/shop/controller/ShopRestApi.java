package com.test.toy_springboot.shop.controller;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopRestApi {
    private ShopService shopService;
    @Autowired
    public ShopRestApi(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<List<Shop>> getShopList(){
        List<Shop> shopList = shopService.getShopList();
        if(shopList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(shopList, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Shop> getShopList(@RequestParam Long shop_id) throws Exception {
        Shop shop = shopService.getShopById(shop_id);
        if(shop == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Shop> saveShop(@RequestBody Shop shop) {
        Shop resultShop = shopService.addShop(shop);
        if(resultShop == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultShop,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Shop> updateDevice(@RequestBody Shop shop){
        Shop resultShop = shopService.updateShop(shop);
        if(resultShop == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultShop,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Shop> deleteDevice(@RequestBody Shop shop){
        shopService.delete(shop);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
