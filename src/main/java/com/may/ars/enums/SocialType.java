package com.may.ars.enums;

import lombok.Getter;

@Getter
public enum SocialType {
    KAKAO("SOCIAL_KAKAO"), GOOGLE("SOCIAL_GOOGLE");

    private String socialName;

    SocialType(String socialName) {
        this.socialName = socialName;
    }
}
