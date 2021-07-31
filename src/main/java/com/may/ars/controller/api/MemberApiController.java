package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.member.GoogleTokenDto;
import com.may.ars.service.OauthService;
import com.may.ars.utils.auth.AuthCheck;
import com.may.ars.utils.auth.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final OauthService oauthService;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleTokenDto tokenDto) {
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "토큰 발급", oauthService.googleLogin(tokenDto.getAccessToken()));
        return ResponseEntity.ok().body(response);
    }

    @AuthCheck
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth() {
        Long memberId = MemberContext.currentMember.get().getId();
        if (memberId != 1L && memberId != 2L) {
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.BAD_REQUEST, "실패"));
        }
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, "성공"));
    }

}
