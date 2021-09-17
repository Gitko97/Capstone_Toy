package com.test.toy_springboot.user.repository;

import com.test.toy_springboot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    

}
