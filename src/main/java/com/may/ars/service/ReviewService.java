package com.may.ars.service;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.problem.ReviewRegisterDto;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.domain.problem.Review;
import com.may.ars.domain.problem.ReviewRepository;
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
    public void registerReview(Long problemId, ReviewRegisterDto registerDto, Member member) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(
                () -> {throw new IllegalArgumentException(NOT_EXIST_PROBLEM);}
        );
        if (!problem.getWriter().getId().equals(member.getId())) {
            throw new IllegalArgumentException(NOT_VALID_USER);
        }
        Review review = registerDto.toEntity(problem);
        reviewRepository.save(review);
    }
}
