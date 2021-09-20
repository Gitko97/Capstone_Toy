package com.test.toy_springboot.category.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String character_;

    @Column
    private String genre;

    @Column
    private String age;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}