package com.test.toy_springboot.toy.service;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {
    private ToyRepository dbAccess;

    @Autowired
    public ToyService(ToyRepository dbAccess) {
        this.dbAccess = dbAccess;
    }

    public List<Toy> getToyList(){
        return dbAccess.findAll();
    }

    public Toy addToy(Toy toy){
        return dbAccess.save(toy);
    }

    public Toy updateToy(Toy toy) {
        return dbAccess.save(toy);
    }

    public void delete(Toy toy) throws IllegalArgumentException{
        dbAccess.delete(toy);
    }
}
