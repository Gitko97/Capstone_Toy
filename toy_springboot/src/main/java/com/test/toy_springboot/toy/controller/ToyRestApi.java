package com.test.toy_springboot.toy.controller;

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
        if(toyList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(toyList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Toy> saveDevice(@RequestBody Toy toy) {
        Toy resultToy = toyService.addToy(toy);
        if(resultToy == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultToy,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Toy> updateDevice(@RequestBody Toy toy){
        Toy resultToy = toyService.updateToy(toy);
        if(resultToy == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultToy,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Toy> deleteDevice(@RequestBody Toy toy){
        toyService.delete(toy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
