package com.test.toy_springboot.user.domain;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_index")
    private Long userIndex;

    @Column(nullable = false, length = 50, unique = true, name = "user_id")
    private String userId;

    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

   @JsonIgnore
   @Column
   private boolean activated;

    @Column
    private String authority;



    @OneToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    @Column(nullable = true)
    private Integer point;

    public void userPointUp(int point){
        this.point += point;
    }
    public void userPointMinus(int point){
        this.point -= point;
    }
    public boolean userComparePoint(int point){
        if (this.point >= point){
            return true;
        }
        return false;
    }
}
