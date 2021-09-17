package com.test.toy_springboot.user.controller;

import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserById(@RequestParam Long idx){
        return ResponseEntity.ok().body(userRepository.findById(idx).orElse(null));
    }
}
