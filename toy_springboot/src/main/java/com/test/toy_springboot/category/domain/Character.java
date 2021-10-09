package com.test.toy_springboot.category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "character_")
public class Character {
    @Id
    @NonNull
    private String name;
}