package com.test.toy_springboot.set.service;

import com.test.toy_springboot.set.domain.Set_goods;
import com.test.toy_springboot.set.repository.Set_goods_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Set_goods_Service {
    private Set_goods_Repository dbAccess;

    @Autowired
    public Set_goods_Service(Set_goods_Repository dbAccess) {
        this.dbAccess = dbAccess;
    }

    public List<Set_goods> getSetList(){
        return dbAccess.findAll();
    }

    public Set_goods addSet(Set_goods set_goods){
        return dbAccess.save(set_goods);
    }

    public void delete(Set_goods set_goods) throws IllegalArgumentException{
        dbAccess.delete(set_goods);
    }

    public Set_goods getSet_goodsById(Long set_id){
        return dbAccess.getById(set_id);
    }
}
