package com.test.toy_springboot.toy.controller;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.service.ShopService;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toy")
public class ToyRestApi {
    private ToyService toyService;
    @Autowired
    public ToyRestApi(ToyService toyService) {
        this.toyService = toyService;
    }

    @GetMapping
    public ResponseEntity<List<Toy>> getToyList(){
        List<Toy> toyList = toyService.getToyList();
        if(toyList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(toyList, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Toy> getToyById(@RequestParam Long toy_id){
        Toy toy = toyService.getToyById(toy_id);
        if(toy == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(toy, HttpStatus.OK);
    }

    @GetMapping("/shop/{shopID}")
    public ResponseEntity<List<Toy>> getNotTradeToyWithShopId(@PathVariable Long shopID){
        List<Toy> toyList = toyService.findNotTradeToyWithShopId(shopID);
        if(toyList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(toyList, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Toy>>  getToyByFilter(@RequestParam(required = false) String character, @RequestParam(required = false) String genre){
        if (character == null && genre == null){
            List<Toy> toyList = toyService.getToyList();
            if(toyList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(toyList, HttpStatus.OK);
        }else if(character == null){
            List<Toy> toyList = toyService.findToyByGenre(genre);
            if(toyList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(toyList, HttpStatus.OK);
        }else if(genre == null){
            List<Toy> toyList = toyService.findToyByCharacter(character);
            if(toyList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(toyList, HttpStatus.OK);
        }else{
            List<Toy> toyList = toyService.findToyByGenreAndCharacter(genre, character);
            if(toyList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(toyList, HttpStatus.OK);
        }
    }

    @PostMapping// 장난감 등록 api
    public ResponseEntity<Toy> saveToy(@RequestBody Toy toy, @RequestParam Long shop_id) {
        try {
            Toy resultToy = toyService.addToy(toy, shop_id);
            return new ResponseEntity<>(resultToy, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Toy> updateToy(@RequestBody Toy toy){
        Toy resultToy = toyService.updateToy(toy);
        if(resultToy == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultToy,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Toy> deleteToy(@RequestBody Toy toy){
        toyService.delete(toy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
