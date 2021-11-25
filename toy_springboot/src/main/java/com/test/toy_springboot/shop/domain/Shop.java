package com.test.toy_springboot.shop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shop_id;

    @JsonManagedReference
    @OneToMany(mappedBy = "shop",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Toy> toyList;


    @OneToOne(mappedBy = "shop",fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
}