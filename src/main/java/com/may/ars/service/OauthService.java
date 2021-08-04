package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.ars.common.advice.exception.JsonWriteException;
import com.may.ars.common.advice.exception.JwtException;
import com.may.ars.config.properties.GoogleProperties;
import com.may.ars.config.properties.KakaoProperties;
import com.may.ars.dto.member.JwtPayload;
import com.may.ars.dto.member.LoginSuccessDto;
import com.may.ars.enums.SocialType;
import com.may.ars.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthService {

    public static final String GRANT_TYPE = "authorization_code";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final MemberService memberService;
    private final JwtService jwtService;

    private final GoogleProperties googleProperties;
    private final KakaoProperties kakaoProperties;

    public LoginSuccessDto kakaoLogin(String code) {
        String accessToken = getAccessToken(code, kakaoProperties.getTokenRequestUrl(), 0);

        JsonNode profile = getProfile(accessToken, kakaoProperties.getProfileRequestUrl());
        String socialId = profile.get("id").textValue();

        log.info(profile.toString());

        String email = profile.get("kakao_account").get("email").textValue();
        Optional<Member> optional = memberService.findMemberByEmail(email);
        Member member;
        if (optional.isEmpty()) { // 회원가입
             member = Member.builder()
                    .email(email)
                    .nickname(profile.get("properties").get("nickname").textValue())
                    .socialType(SocialType.KAKAO)
                    .socialId(socialId)
                    .build();

            memberService.saveMember(member);
        } else { // 로그인
            member = optional.get();
        }

        String token = jwtService.createToken(new JwtPayload(member.getId(), email));

        return LoginSuccessDto.builder()
                .nickname(member.getNickname())
                .access_token(token)
                .build();
    }

    public LoginSuccessDto googleLoginByCode(String code) {
        String accessToken = getAccessToken(code, googleProperties.getTokenRequestUrl(), 1);
        JsonNode profile = getProfile(accessToken, googleProperties.getProfileRequestUrl());

        Member member;

        String email = profile.get("email").textValue();
        Optional<Member> optional = memberService.findMemberByEmail(email);

        if (optional.isEmpty()) { // 회원가입
            member = Member.builder()
                    .email(email)
                    .nickname(profile.get("name").textValue())
                    .socialType(SocialType.GOOGLE)
                    .socialId(profile.get("sub").textValue())
                    .build();

            memberService.saveMember(member);
        } else { // 로그인
            member = optional.get();
        }

        String token = jwtService.createToken(new JwtPayload(member.getId(), email));

        return LoginSuccessDto.builder()
                .nickname(member.getNickname())
                .access_token(token)
                .build();
    }

    public LoginSuccessDto googleLogin(String accessToken) {
        JsonNode profile = getProfile(accessToken, googleProperties.getProfileRequestUrl());
        String email = profile.get("email").textValue();
        Optional<Member> optional = memberService.findMemberByEmail(email);
        Member member;

        if (optional.isEmpty()) { // 불가
            throw new JwtException();
        } else {                 // 로그인
            member = optional.get();

            String token = jwtService.createToken(new JwtPayload(member.getId(), email));

            return LoginSuccessDto.builder()
                    .nickname(member.getNickname())
                    .access_token(token)
                    .build();
        }
    }

    private String getAccessToken(String code, String tokenRequestUrl, int socialType) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(code);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("code", code);
        if (socialType == 0) {
            params.add("client_id", kakaoProperties.getClientId());
            params.add("redirect_uri", kakaoProperties.getRedirectUri());
        } else {
            params.add("client_id", googleProperties.getClientId());
            params.add("client_secret", googleProperties.getSecretKey());
            params.add("redirect_uri", googleProperties.getRedirectUri());
        }
        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                tokenRequestUrl,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );
        return readBody(response.getBody()).get("access_token").toString();
    }

    private JsonNode getProfile(String accessToken, String profileRequestUrl) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> restResponse = restTemplate.exchange(
                profileRequestUrl,
                HttpMethod.POST,
                profileRequest,
                String.class
        );
        log.info(restResponse.getBody());
        return readBody(restResponse.getBody());
    }

    private JsonNode readBody(String responseBody) {
        try {
            return objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new JsonWriteException();
        }
    }
}
