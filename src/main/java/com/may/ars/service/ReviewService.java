package com.may.ars.service;

import com.may.ars.common.advice.exception.EntityNotFoundException;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.review.Review;
import com.may.ars.dto.ReviewRequestDto;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.domain.review.ReviewRepository;
import com.may.ars.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.may.ars.common.advice.ExceptionMessage.*;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;

    private final ProblemRepository problemRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void registerReview(Long problemId, ReviewRequestDto registerDto, Member member) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(EntityNotFoundException::new);
        problem.setNotificationDate(registerDto.getNotificationDate());
        if (!problem.getWriter().getId().equals(member.getId())) {
            throw new IllegalArgumentException(NOT_VALID_USER);
        }
        problemRepository.save(problem);
        reviewRepository.save(reviewMapper.toEntity(problem, registerDto));
    }

    @Transactional
    public void updateReview(Long reviewId, Review review, Member member) {
        checkValidUser(reviewId, member);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId, Member member) {
        checkValidUser(reviewId, member);
        reviewRepository.deleteById(reviewId);
    }

    @Transactional(readOnly = true)
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
    }

    private void checkValidUser(Long reviewId, Member member) {
        reviewRepository.findReviewByIdAndMemberId(reviewId, member.getId()).orElseThrow(EntityNotFoundException::new);
    }
}
