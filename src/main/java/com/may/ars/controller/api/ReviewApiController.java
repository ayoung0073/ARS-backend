package com.may.ars.controller.api;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.review.ReviewRequestDto;
import com.may.ars.mapper.ReviewMapper;
import com.may.ars.service.ReviewService;
import com.may.ars.utils.auth.AuthCheck;
import com.may.ars.utils.auth.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewApiController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @AuthCheck
    @PostMapping("/problems/{problemId}/reviews")
    public ResponseEntity<?> saveReview(@PathVariable("problemId") Long problemId, @RequestBody ReviewRequestDto registerDto) {
        Member member = MemberContext.currentMember.get();

        reviewService.registerReview(problemId, registerDto, member);
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "리뷰 등록 성공");
        return ResponseEntity.ok().body(response);
    }

    @AuthCheck
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequestDto registerDto) {
        Member member = MemberContext.currentMember.get();

        reviewService.updateReview(reviewId, reviewMapper.toEntity(reviewId, registerDto), member);
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "리뷰 수정 성공");
        return ResponseEntity.ok().body(response);
    }
}
