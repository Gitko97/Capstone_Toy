package com.test.toy_springboot.set.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.toy_springboot.config.AuditingEntity;
import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Set_goods extends AuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long set_id;

    @Column
    private String set_name;

    @Column
    private int bestPoint = 0;

    @OneToOne
    @JoinColumn(name="best_point_user")
    private User bestPointUser;

    @OneToMany(mappedBy = "set_goods", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Toy> toy;

    public Set_goods(String set_name) {
        this.set_name = set_name;
    }
    public boolean compareBestPoint(int point){
        if(this.bestPoint >= point){
            return true;
        }
        return false;
    }
}