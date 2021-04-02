package com.may.ars.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthToken {
    // 카카오 토큰 요청 후, 응답 Body
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
