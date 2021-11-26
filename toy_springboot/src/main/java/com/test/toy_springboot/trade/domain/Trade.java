package com.test.toy_springboot.trade.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.toy_springboot.config.AuditingEntity;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trade extends AuditingEntity {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trade_id;

    @OneToMany
    @JoinColumn(name = "from_toy_id")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    List<Toy> from_toy = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "to_toy_id")
    List<Toy> to_toy = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "from_user")
    User from_user;

    @OneToOne
    @JoinColumn(name = "to_user")
    User to_user;

    @Column
    private Integer trade_status = 0; // 0 init 1 수락 2 거절
}
