package com.test.toy_springboot.category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Category_set {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long set_id;

    @Column
    private String set_name;

    @Column
    private int set_num;

    public Category_set(String set_name, int set_num) {
        this.set_name = set_name;
        this.set_num = set_num;
//        this.toy_id_list = "";
    }

//    public Category_set(String set_name, int set_num, String str) {
//        this.set_name = set_name;
//        this.set_num = set_num;
//        this.toy_id_list = str;
//    }
//
//    public void addToyId(Long toy_id){
//        if (this.toy_id_list.length() == 0)
//            this.toy_id_list += toy_id.toString();
//        else this.toy_id_list += "," + toy_id.toString();
//    }
//
//    public boolean checkToyListNull(){
//        return this.toy_id_list.length() <= 0;
//    }
//
//    public int popToy(){
//        int firstIndex = this.toy_id_list.indexOf(",");
//        int toy_id = Integer.parseInt(this.toy_id_list.substring(0, firstIndex));
//        this.toy_id_list = this.toy_id_list.substring(toy_id);
//        return toy_id;
//    }
}