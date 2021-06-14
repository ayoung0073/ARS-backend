package com.may.ars.controller.api;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.review.ReviewRegisterDto;
import com.may.ars.service.ReviewService;
import com.may.ars.utils.AuthCheck;
import com.may.ars.utils.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/problems")
public class ReviewApiController {

    private final ReviewService reviewService;

    @AuthCheck
    @PostMapping("/{problemId}/reviews")
    public ResponseEntity<?> saveReview(@PathVariable("problemId") Long problemId, @RequestBody ReviewRegisterDto registerDto) {
        Member member = MemberContext.currentMember.get();

        reviewService.registerReview(problemId, registerDto, member);
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "리뷰 등록 성공");
        return ResponseEntity.ok().body(response);
    }
}
