package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.ars.dto.JwtPayload;
import com.may.ars.dto.MemberDto;
import com.may.ars.dto.member.LoginSuccessDto;
import com.may.ars.enums.SocialType;
import com.may.ars.model.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OauthService {

    public static final String GRANT_TYPE = "authorization_code";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final MemberService memberService;
    private final JwtService jwtService;


    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.token_request_url}")
    private String kakaoTokenUrl;

    @Value("${kakao.profile_request_url}")
    private String kakaoProfileUrl;

    @Value("${google.redirect_uri}")
    private String googleRedirectUri;

    @Value("${google.client_id}")
    private String googleClientId;

    @Value("${google.secret_key}")
    private String googleSecretKey;

    @Value("${google.token_request_url}")
    private String googleTokenUrl;

    @Value("${google.profile_request_url}")
    private String googleProfileUrl;


    public LoginSuccessDto kakaoLogin(String code) throws JsonProcessingException {
        String accessToken = getAccessToken(code, kakaoTokenUrl, 0);
        System.out.println(accessToken);
        JsonNode profile = getProfile(accessToken, kakaoProfileUrl);
        MemberDto memberDto;
        JwtPayload jwtPayload;
        String email = profile.get("kakao_account").get("email").toString();
        Optional<Member> optional = memberService.findMemberByEmail(email);

        if (optional.isEmpty()) { // 회원가입 처리
            memberDto = new MemberDto();
            memberDto.setEmail(email);
            memberDto.setNickname(profile.get("properties").get("nickname").toString());
            memberDto.setSocialType(SocialType.KAKAO);

            Long memberId = memberService.saveMember(memberDto);
            jwtPayload = new JwtPayload(memberId, email);
        }
        else { // 로그인 처리
            memberDto = MemberDto.fromEntity(optional.get());
            jwtPayload = new JwtPayload(memberDto.getMemberId(), email);
        }

        String token = jwtService.createToken(jwtPayload);

        return LoginSuccessDto.builder()
                .nickname(memberDto.getNickname())
                .access_token(token)
                .build();
    }

    public LoginSuccessDto googleLogin(String code) throws JsonProcessingException {
        String accessToken = getAccessToken(code, googleTokenUrl, 1);
        System.out.println(accessToken);
        JsonNode profile = getProfile(accessToken, googleProfileUrl);

        MemberDto memberDto;
        JwtPayload jwtPayload;

        String email = profile.get("email").toString();
        Optional<Member> optional = memberService.findMemberByEmail(email);

        if (optional.isEmpty()) { // 회원가입 처리
            memberDto = new MemberDto();
            memberDto.setEmail(email);
            memberDto.setNickname(profile.get("name").toString());
            memberDto.setSocialType(SocialType.GOOGLE);

            Long memberId = memberService.saveMember(memberDto);
            jwtPayload = new JwtPayload(memberId, email);
        }
        else { // 로그인 처리
            memberDto = MemberDto.fromEntity(optional.get());
            jwtPayload = new JwtPayload(memberDto.getMemberId(), email);
        }

        String token = jwtService.createToken(jwtPayload);

        return LoginSuccessDto.builder()
                .nickname(memberDto.getNickname())
                .access_token(token)
                .build();
    }

    private String getAccessToken(String code, String tokenRequestUrl, int socialType) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(code);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("code", code);
        if (socialType == 0) {
            params.add("client_id", kakaoClientId);
            params.add("redirect_uri", kakaoRedirectUri);
        } else {
            params.add("client_id", googleClientId);
            params.add("client_secret", googleSecretKey);
            params.add("redirect_uri", googleRedirectUri);
        }
        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<>(params, headers);

        // 카카오 프로필 정보에 접근할 토큰 받기
        ResponseEntity<String> response = restTemplate.exchange(
                tokenRequestUrl,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );
        return objectMapper.readTree(response.getBody()).get("access_token").toString();
    }

    private JsonNode getProfile(String accessToken, String profileRequestUrl) throws JsonProcessingException {
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
        return objectMapper.readTree(restResponse.getBody());
    }
}
