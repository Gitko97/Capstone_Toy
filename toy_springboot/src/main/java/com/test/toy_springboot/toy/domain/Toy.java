package com.test.toy_springboot.toy.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.toy_springboot.category.domain.Category_set;
import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.config.AuditingEntity;
import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.set.domain.Set_goods;
import com.test.toy_springboot.shop.domain.Shop;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Toy extends AuditingEntity {
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_set_id")
    private Category_set category_set;

    @Column
    private String productName;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime setTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Set_goods set_goods;

    public void setSetTime(){
        this.setTime = LocalDateTime.now();
    }
}
