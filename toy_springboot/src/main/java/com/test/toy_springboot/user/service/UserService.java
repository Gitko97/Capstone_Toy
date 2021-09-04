package com.test.toy_springboot.user.service;

import com.test.toy_springboot.user.domain.User;
import com.test.toy_springboot.user.dto.SignUpRequest;
import com.test.toy_springboot.user.dto.UserInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     *
     * @param signUpRequest 유저 로그인 객체
     * @return 성공 1, 실패 0
     */
    public int signUp(SignUpRequest signUpRequest) {
        User user = signUpRequest.toEntity();



        return 1;
    }

//    public UserInfoResponse getUserInfo(String userId){
//         User user = repository.getByUserId(userId); DB 유저 아이디 넘어옴
//         return UserInfoResponse.form(user);
//    }


}
