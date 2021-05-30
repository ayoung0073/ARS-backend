package com.may.ars.dto.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginSuccessDto {
    private String nickname;
    private String access_token;
}
