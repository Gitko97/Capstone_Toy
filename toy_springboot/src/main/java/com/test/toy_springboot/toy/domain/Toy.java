package com.test.toy_springboot.toy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.toy_springboot.shop.domain.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shop_id")
    @JsonIgnore
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 후에 에러 날시 테스트
    private Shop shop;

    @Column(nullable = false)
   private String productName;

    @Builder
    public Toy(String productName) {
        this.productName = productName;
    }



}
