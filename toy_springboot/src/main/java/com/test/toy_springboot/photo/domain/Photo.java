package com.test.toy_springboot.photo.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.toy_springboot.toy.domain.Toy;
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


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photo_id;

    @ManyToOne
    @JoinColumn(name="toy_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonBackReference
    private Toy toy;

    @Column
    @JsonIgnore
    private String filePath;

    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public byte[] getImageByte() throws IOException {
        InputStream imageStream = new FileInputStream(this.filePath);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }
    public Photo(String filePath) {
        this.filePath = filePath;
    }
}