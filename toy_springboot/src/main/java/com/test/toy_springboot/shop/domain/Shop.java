package com.test.toy_springboot.shop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(mappedBy = "shop",fetch = FetchType.EAGER)
    private List<Toy> toyList;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}