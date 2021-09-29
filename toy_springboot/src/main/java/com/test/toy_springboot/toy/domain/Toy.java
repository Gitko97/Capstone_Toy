package com.test.toy_springboot.toy.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.shop.domain.Shop;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Toy {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toy_id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="shop_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonBackReference
    private Shop shop;

    @OneToMany(mappedBy = "toy")
    @JsonManagedReference
    private List<Photo> photo;

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
