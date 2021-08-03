package com.may.ars.controller.api;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.review.ReviewRequestDto;
import com.may.ars.mapper.ProblemMapper;
import com.may.ars.mapper.ReviewMapper;
import com.may.ars.service.ProblemService;
import com.may.ars.service.ReviewService;
import com.may.ars.utils.auth.AuthCheck;
import com.may.ars.utils.auth.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.may.ars.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewApiController {

    private final ProblemService problemService;
    private final ReviewService reviewService;

    private final ProblemMapper problemMapper;
    private final ReviewMapper reviewMapper;

    @AuthCheck
    @PostMapping("/problems/{problemId}/reviews")
    public ResponseEntity<?> saveReview(@PathVariable("problemId") Long problemId, @RequestBody ReviewRequestDto registerDto) {
        Member member = MemberContext.currentMember.get();

        reviewService.registerReview(problemId, registerDto, member);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_REGISTER_REVIEW));
    }

    @AuthCheck
    @PutMapping("/problems/{problemId}/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("problemId") Long problemId, @PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequestDto requestDto) {
        Member member = MemberContext.currentMember.get();
        problemService.updateProblem(problemMapper.toEntity(problemId, requestDto, member));
        reviewService.updateReview(reviewId, reviewMapper.toEntity(reviewId, requestDto), member);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_UPDATE_REVIEW));
    }

    @AuthCheck
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId) {
        Member member = MemberContext.currentMember.get();

        reviewService.deleteReview(reviewId, member);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_DELETE_REVIEW));
    }
}
