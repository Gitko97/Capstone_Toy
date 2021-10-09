package com.test.toy_springboot.set.repository;

import com.test.toy_springboot.set.domain.Set_goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Set_goods_Repository extends JpaRepository<Set_goods, Long> {
}
