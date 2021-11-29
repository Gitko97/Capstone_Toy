package com.test.toy_springboot.category.repository;

import com.test.toy_springboot.category.domain.Category_set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Category_setRepository extends JpaRepository<Category_set, Long> {
    @Transactional
    @Query(value = "SELECT * From category_set WHERE set_name=:name", nativeQuery = true)
    List<Category_set> findAllBySetName(@Param("name")String name);

    @Transactional
    @Query(value = "SELECT * From category_set AS c WHERE c.set_name=:name AND c.set_num=:num ", nativeQuery = true)
    Category_set getCategorySetByNameAndNum(@Param("name")String name, @Param("num")int num);
}
