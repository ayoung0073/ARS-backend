package com.may.ars.dto;

import lombok.Data;

@Data
public class KakaoProfile {

    public Integer id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public static class Properties {
        public String nickname;
    }

    @Data
    public static class KakaoAccount {
        public Boolean profile_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
    }

    @Data
    public static class Profile {
        public String nickname;
    }

}
