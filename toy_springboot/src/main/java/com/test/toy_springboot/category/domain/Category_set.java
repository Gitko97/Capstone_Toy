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
    }

}