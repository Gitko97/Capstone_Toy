package com.test.toy_springboot.category.repository;

import com.test.toy_springboot.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Category, String> {
}
