package com.test.toy_springboot.toy.repository;

import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToyRepository extends JpaRepository<Toy, Long> {
}
