package com.test.toy_springboot.toy.repository;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.user.domain.User;
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
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE toy t SET t.set_id = :set_id WHERE t.toy_id = :toy_id", nativeQuery = true)
    void match_toy_to_setItem(@Param("toy_id")Long toy_id, @Param("set_id")Long set_id);
}
