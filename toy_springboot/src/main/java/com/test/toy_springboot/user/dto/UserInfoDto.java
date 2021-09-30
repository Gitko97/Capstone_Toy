package com.test.toy_springboot.user.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.toy_springboot.user.domain.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String userId;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull
//    @Size(min = 3, max = 100)
//    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 11, max = 50)
    private String phoneNumber;

    // post 관련 정보

    static public UserInfoDto from(User user){
        return UserInfoDto.builder().userId(user.getUserId()).name(user.getName()).phoneNumber(user.getPhoneNumber()).build();
    }


}