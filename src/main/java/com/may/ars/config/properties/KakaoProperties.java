package com.may.ars.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "social.kakao")
@Getter
@Setter
public class KakaoProperties {
    private String clientId;
    private String redirectUri;
    private String tokenRequestUrl;
    private String profileRequestUrl;
}
