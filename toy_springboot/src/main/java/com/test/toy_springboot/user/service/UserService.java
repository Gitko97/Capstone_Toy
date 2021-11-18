package com.test.toy_springboot.user.service;

import com.test.toy_springboot.shop.domain.Shop;
import com.test.toy_springboot.shop.service.ShopService;
import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.dto.SignUpDto;
import com.test.toy_springboot.user.dto.UserInfoDto;
import com.test.toy_springboot.user.repository.UserRepository;
import com.test.toy_springboot.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopService shopService;

    @Transactional
    public User signup(SignUpDto signUpDto) {
        if (userRepository.findOneByUserId(signUpDto.getUserId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        //빌더 패턴의 장점
//        Authority authority = Authority.builder()
//                .authorityName("ROLE_USER")
//                .build();

        User user = User.builder()
                .userId(signUpDto.getUserId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .name(signUpDto.getName())
                .phoneNumber(signUpDto.getPhoneNumber())
                .authority("ROLE_USER")
                .activated(true)
                .build();
        Shop newShop = new Shop();
        newShop.setUser(user);
        shopService.addShop(newShop);
        user.setShop(newShop);
        user.setPoint(0);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneByUserId(username);
    }

    @Transactional
    public void userPointUp(String username, int point) throws Exception {
        User user=  userRepository.findOneByUserId(username).orElseThrow(() -> new Exception("Cannot find User ID"));
        user.userPointUp(point);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneByUserId);
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfoById(String userId) {
        User user = userRepository.findOneByUserId(userId).orElseThrow(NoSuchElementException::new);

        return UserInfoDto.from(user);
    }

    @Transactional
    public User getUserById(String userId) throws Exception {
        User user = userRepository.findOneByUserId(userId).orElseThrow(() -> new Exception("User ID Error"));
        return user;
    }
}