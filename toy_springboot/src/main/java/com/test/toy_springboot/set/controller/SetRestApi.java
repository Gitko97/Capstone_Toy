package com.test.toy_springboot.set.controller;

import com.test.toy_springboot.category.domain.Category_set;
import com.test.toy_springboot.category.service.CategoryService;
import com.test.toy_springboot.set.domain.Set_goods;
import com.test.toy_springboot.set.service.Set_goods_Service;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/setGoods")
public class SetRestApi {
    private Set_goods_Service set_goods_service;
    private ToyService toyService;
    private CategoryService categoryService;

    @Autowired
    public SetRestApi(Set_goods_Service set_goods_service,ToyService toyService, CategoryService categoryService) {
        this.set_goods_service = set_goods_service;
        this.toyService = toyService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Set_goods>> getPhotoList(){
        List<Set_goods> setList = set_goods_service.getSetList();
        if(setList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(setList, HttpStatus.OK);
    }

    @PostMapping("/{toy_id}")
    public ResponseEntity<String> registToyToSet(@PathVariable String toy_id){
        Toy resultToy = toyService.getToyById(Long.parseLong(toy_id));
        if (resultToy.getCategory_set() == null) return new ResponseEntity<>("This is not set Item",HttpStatus.BAD_REQUEST);
        resultToy.setSetTime();
        toyService.updateToy(resultToy);
        List<Long> toyList = categoryService.checkSet(resultToy.getCategory_set().getSet_name());
        if(toyList == null) return new ResponseEntity<>(HttpStatus.OK);
        Set_goods result_set = set_goods_service.addSet(new Set_goods(resultToy.getCategory_set().getSet_name()));
        for (Long toyId : toyList){
            toyService.updateToySetGoods(toyId, result_set.getSet_id());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Set_goods> deleteDevice(@RequestBody Set_goods set_goods){
        set_goods_service.delete(set_goods);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
