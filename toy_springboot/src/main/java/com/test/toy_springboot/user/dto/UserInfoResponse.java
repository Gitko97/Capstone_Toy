package com.test.toy_springboot.user.dto;

import com.test.toy_springboot.user.domain.User;
import lombok.Builder;

@Builder
public class UserInfoResponse {
    private String userId;
    private String pw;
    private String name;
    private String phoneNumber;

    public static UserInfoResponse form(User user){

        return UserInfoResponse.builder().userId(user.getUserId()).pw(user.getPw()).name(user.getName()).phoneNumber(user.getPhoneNumber()).build();
    }


}
