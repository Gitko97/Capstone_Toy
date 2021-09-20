package com.test.toy_springboot.category.repository;

import com.test.toy_springboot.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
