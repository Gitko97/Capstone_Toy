package com.test.toy_springboot.toy.repository;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ToyRepository extends JpaRepository<Toy, Long> {
    @Transactional
    @Query(value = "SELECT t.toy_id From toy t WHERE t.set_id IS NULL AND t.set_time IS NOT NULL AND t.category_set_id=:id ORDER BY t.set_time ASC LIMIT 1", nativeQuery = true)
    Long findToyBySetId(@Param("id")Long id);

    @Transactional
    @Query(value = "SELECT * From toy t WHERE t.character_id=:character_name ORDER BY t.created_date ASC", nativeQuery = true)
    List<Toy> findToyByCharacter(@Param("character_name")String character_name);
    @Transactional
    @Query(value = "SELECT * From toy t WHERE t.genre_id=:genre_name ORDER BY t.created_date ASC", nativeQuery = true)
    List<Toy> findToyByGenre(@Param("genre_name")String genre_name);
    @Transactional
    @Query(value = "SELECT * From toy t WHERE t.genre_id=:genre_name AND t.character_id=:character_name ORDER BY t.created_date ASC", nativeQuery = true)
    List<Toy> findToyByGenreAndCharacter(@Param("genre_name")String genre_name, @Param("character_name")String character_name);

    @Transactional
    @Query(value = "SELECT * FROM toy t WHERE t.shop_id=:shop_id AND t.trade_status = 0 AND t.set_time is NULL", nativeQuery = true)
    List<Toy> findNotTradeToyWithShopId(@Param("shop_id")Long shop_id);

    @Transactional
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE toy t SET t.set_id = :set_id WHERE t.toy_id = :toy_id", nativeQuery = true)
    void match_toy_to_setItem(@Param("toy_id")Long toy_id, @Param("set_id")Long set_id);


    @Modifying
    @Query("UPDATE Toy t SET t.tradeStatus = 1 WHERE t.toy_id = :toy_id")
    void updateToyToStatusComplete(@Param("toy_id")Long toy_id);

    @Modifying
    @Query("UPDATE Toy t SET t.tradeStatus = 0 WHERE t.toy_id = :toy_id")
    void updateToyToStatusTrade(@Param("toy_id")Long toy_id);
}
