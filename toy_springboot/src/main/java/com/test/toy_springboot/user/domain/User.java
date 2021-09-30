package com.test.toy_springboot.user.domain;

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

    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

   @JsonIgnore
   @Column
   private boolean activated;

    @Column
    private String authority;

//   @ManyToMany
//   @JoinTable(
//           name = "user_authority",
//           joinColumns = {@JoinColumn(name = "user_index", referencedColumnName = "user_index")},
//           inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})

}
