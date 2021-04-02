package com.may.ars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.ars.dto.KakaoProfile;
import com.may.ars.dto.MemberDto;
import com.may.ars.dto.OAuthToken;
import com.may.ars.enums.SocialType;
import com.may.ars.model.entity.Member;
import com.may.ars.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "/user/login";
    }

    /**
     * Method 카카오 로그인
     *
     * @param code
     * @see /user/kakao/callback
     */
    @GetMapping("/kakao/callback")
    public String kakaoCallback(String code) throws Exception {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // 카카오 프로필 정보에 접근할 토큰 받기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        // 카카오 프로필 정보 받기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );


        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);

        Optional<Member> optional = memberService.findMemberByEmail(kakaoProfile.getKakao_account().getEmail());

        if (optional.isEmpty()) { // 회원가입 처리
            MemberDto memberDto = new MemberDto();
            memberDto.setEmail(kakaoProfile.getKakao_account().getEmail());
            memberDto.setNickname(kakaoProfile.getProperties().getNickname());
            memberDto.setSocialType(SocialType.KAKAO);

            memberService.saveMember(memberDto);
        }
        else { // 로그인 처리
            MemberDto memberDto = MemberDto.fromEntity(optional.get());
        }

        return "redirect:/";

    }
}
