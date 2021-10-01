package com.test.toy_springboot.user.repository;

import com.test.toy_springboot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @EntityGraph(attributePaths = "authority")
    Optional<User> findOneByUserId(String userId);

}
