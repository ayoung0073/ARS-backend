package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.member.GoogleTokenDto;
import com.may.ars.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.may.ars.common.message.SuccessMessage.SUCCESS_ISSUE_TOKEN;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final OauthService oauthService;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleTokenDto tokenDto) {
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_ISSUE_TOKEN, oauthService.googleLogin(tokenDto.getAccessToken()))
        );
    }

}
