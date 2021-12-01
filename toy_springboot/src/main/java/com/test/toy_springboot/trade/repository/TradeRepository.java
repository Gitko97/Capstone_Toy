package com.test.toy_springboot.trade.repository;

import com.test.toy_springboot.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Transactional
    @Query(value = "SELECT * From trade t WHERE t.from_user=:userIndex ORDER BY t.created_date DESC", nativeQuery = true)
    List<Trade> findTradeByFromUser(@Param("userIndex")Long userIndex);

    @Transactional
    @Query(value = "SELECT * From trade t WHERE t.to_user=:userIndex ORDER BY t.created_date DESC", nativeQuery = true)
    List<Trade> findTradeByToUser(@Param("userIndex")Long userIndex);
}
