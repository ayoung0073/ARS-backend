package com.may.ars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.ars.dto.*;
import com.may.ars.enums.SocialType;
import com.may.ars.model.entity.Member;
import com.may.ars.service.JwtService;
import com.may.ars.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Value("${google.client_id}")
    private String googleClientId;

    @Value("${google.secret_key}")
    private String googleSecretKey;

    private final MemberService memberService;
    private final JwtService jwtService;

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
    public String kakaoCallback(String code, Model model) throws Exception {

        RestTemplate rt = new RestTemplate();
        log.info("code : " + code);
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
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

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

        MemberDto memberDto;

        if (optional.isEmpty()) { // 회원가입 처리
            memberDto = new MemberDto();
            memberDto.setEmail(kakaoProfile.getKakao_account().getEmail());
            memberDto.setNickname(kakaoProfile.getProperties().getNickname());
            memberDto.setSocialType(SocialType.KAKAO);

            memberService.saveMember(memberDto);
        }
        else { // 로그인 처리
            memberDto = MemberDto.fromEntity(optional.get());
        }

        // 액세스 토큰 발급
        JwtPayload jwtPayload = new JwtPayload(memberDto.getMemberId(), memberDto.getEmail());
        String token = jwtService.createToken(jwtPayload);

        log.info("토큰 발급 : " + token);

        model.addAttribute("access_token", token);
        model.addAttribute("nickname", memberDto.getNickname());

        return "index";
    }

    /**
     * Method 구글 로그인
     *
     * @param code
     * @see /user/google/callback
     */
    @GetMapping("/google/callback")
    public String googleCallback(String code, Model model) throws Exception {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleClientId);
        params.add("client_secret", googleSecretKey);
        params.add("redirect_uri", "http://127.0.0.1:8080/user/google/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> googleTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

        RestTemplate rt2 = new RestTemplate();


        
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> googleProfileRequest =
                new HttpEntity<>(headers2);

        // 구글 프로필 정보 받기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.POST,
                googleProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        System.out.println(response2.getBody());

        GoogleProfile googleProfile = objectMapper2.readValue(response2.getBody(), GoogleProfile.class);

        Optional<Member> optional = memberService.findMemberByEmail(googleProfile.getEmail());

        MemberDto memberDto;

        if (optional.isEmpty()) { // 회원가입 처리
            log.info(googleProfile.getEmail() + " : 회원가입 처리");
            memberDto = new MemberDto();
            memberDto.setEmail(googleProfile.getEmail());
            memberDto.setNickname(googleProfile.getName());
            memberDto.setSocialType(SocialType.GOOGLE);

            memberService.saveMember(memberDto);
        }
        else { // 로그인 처리
            log.info(googleProfile.getEmail() + " : 로그인 처리");
            memberDto = MemberDto.fromEntity(optional.get());
        }

        // 액세스 토큰 발급
        JwtPayload jwtPayload = new JwtPayload(memberDto.getMemberId(), memberDto.getEmail());
        String token = jwtService.createToken(jwtPayload);

        log.info("토큰 발급 : " + token);

        model.addAttribute("access_token", token);
        model.addAttribute("nickname", memberDto.getNickname());

        return "index"; // redirect 안하면 토큰 다시 요청.
    }

}
