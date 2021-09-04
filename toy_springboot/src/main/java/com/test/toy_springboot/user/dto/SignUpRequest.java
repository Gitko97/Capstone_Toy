package com.test.toy_springboot.user.dto;

import com.test.toy_springboot.user.domain.User;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String userId;
    private String pw;
    private String name;
    private String phoneNumber;

    public User toEntity(){
        return User.builder().userId(userId).pw(pw).name(name).phoneNumber(phoneNumber).build();
    }

}
