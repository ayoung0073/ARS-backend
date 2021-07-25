package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.utils.auth.AuthCheck;
import com.may.ars.utils.auth.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    @AuthCheck
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth() {
        Long memberId = MemberContext.currentMember.get().getId();
        log.info(memberId + "");
        if (memberId != 1L && memberId != 2L) {
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.BAD_REQUEST, "실패"));
        }
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, "성공"));
    }

}
