package com.test.toy_springboot.toy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.shop.domain.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toy_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shop_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 후에 에러 날시 테스트
    private Shop shop;

    @OneToOne(mappedBy = "toy",fetch = FetchType.EAGER)
    private Photo photo;

    @OneToOne
    @JoinColumn(name="character_id")
    private Character character;

    @OneToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @Column(nullable = false)
   private String productName;

    @Builder
    public Toy(String productName) {
        this.productName = productName;
    }

}
