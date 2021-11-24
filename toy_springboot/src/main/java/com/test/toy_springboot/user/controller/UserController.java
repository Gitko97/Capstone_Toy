package com.test.toy_springboot.user.controller;

import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.dto.SignUpDto;
import com.test.toy_springboot.user.dto.UserInfoDto;
import com.test.toy_springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<User> signup(
            @Valid @RequestBody SignUpDto signUpDto
    ) {
        return ResponseEntity.ok(userService.signup(signUpDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<User> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().orElse(null));
    }
    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoDto> showUserInfo(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user ){
        return ResponseEntity.ok(userService.getUserInfoById(user.getUsername()));
    }

    @GetMapping("/role")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Boolean> getUserRole(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user ){

        return ResponseEntity.ok(true);
    }


}