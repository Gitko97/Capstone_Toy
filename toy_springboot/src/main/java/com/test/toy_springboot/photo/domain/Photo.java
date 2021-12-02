package com.test.toy_springboot.photo.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.toy_springboot.config.AuditingEntity;
import com.test.toy_springboot.toy.domain.Toy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Photo extends AuditingEntity {
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

    public String getImageByte(){
        try {
            InputStream imageStream = new FileInputStream(this.filePath);
            byte[] imageByteArray = IOUtils.toByteArray(imageStream);
            String resultBase64Encoded = Base64.getEncoder().encodeToString(imageByteArray);
            imageStream.close();
            return resultBase64Encoded;
        }
        catch (Exception e){
            return null;
        }
    }
    public Photo(String filePath) {
        this.filePath = filePath;
    }
}