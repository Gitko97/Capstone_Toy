package com.test.toy_springboot.post.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String productName;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String toyTranslate;

    @Column(nullable = false)
    private String context;

    @Column(nullable = false)
    private Boolean exchangeState;

    @Builder
    public Post(String productName) {
        this.productName = productName;
    }



}