package com.may.ars.controller;

import com.may.ars.config.properties.GoogleProperties;
import com.may.ars.config.properties.KakaoProperties;
import com.may.ars.dto.member.LoginSuccessDto;
import com.may.ars.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final OauthService oauthService;
    private final GoogleProperties googleProperties;
    private final KakaoProperties kakaoProperties;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("googleRedirectUri", googleProperties.getRedirectUri());
        model.addAttribute("kakaoRedirectUri", kakaoProperties.getRedirectUri());
        return "user/login";
    }

    /**
     * Method 카카오 로그인
     */
    @GetMapping("/kakao/callback")
    public String kakaoCallback(String code, Model model) throws Exception {
        LoginSuccessDto successDto = oauthService.kakaoLogin(code);

        model.addAttribute("access_token", successDto.getAccess_token());
        model.addAttribute("nickname", successDto.getNickname());

        return "redirect:/";
    }

    /**
     * Method 구글 로그인
     */
    @GetMapping("/google/callback")
    public String googleCallback(String code, Model model) throws Exception {
        LoginSuccessDto successDto = oauthService.googleLogin(code);

        model.addAttribute("access_token", successDto.getAccess_token());
        model.addAttribute("nickname", successDto.getNickname());

        return "redirect:/";
    }
}