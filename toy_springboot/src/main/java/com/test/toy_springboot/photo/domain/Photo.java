package com.test.toy_springboot.photo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name="toy_id")
    @JsonIgnore
    private Toy toy;

    @Column
    private String url;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}