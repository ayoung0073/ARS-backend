package com.may.ars.service;

import com.may.ars.dto.member.MemberDto;
import com.may.ars.dto.problem.ReviewRegisterDto;
import com.may.ars.model.entity.problem.Problem;
import com.may.ars.model.entity.problem.ProblemRepository;
import com.may.ars.model.entity.problem.Review;
import com.may.ars.model.entity.problem.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.may.ars.response.ErrorMessage.NOT_EXIST_PROBLEM;
import static com.may.ars.response.ErrorMessage.NOT_VALID_USER;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ProblemRepository problemRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void registerReview(Long problemId, ReviewRegisterDto registerDto, MemberDto member) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(
                () -> {throw new IllegalArgumentException(NOT_EXIST_PROBLEM);}
        );
        if (!problem.getWriter().getId().equals(member.getMemberId())) {
            throw new IllegalArgumentException(NOT_VALID_USER);
        }
        Review review = registerDto.toEntity(problem);
        reviewRepository.save(review);
    }
}