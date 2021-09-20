package com.test.toy_springboot.shop.repository;

import com.test.toy_springboot.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
