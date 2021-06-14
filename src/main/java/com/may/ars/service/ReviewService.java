package com.may.ars.service;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.review.ReviewRegisterDto;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.domain.review.ReviewRepository;
import com.may.ars.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.may.ars.response.ErrorMessage.NOT_EXIST_PROBLEM;
import static com.may.ars.response.ErrorMessage.NOT_VALID_USER;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;

    private final ProblemRepository problemRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void registerReview(Long problemId, ReviewRegisterDto registerDto, Member member) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(
                () -> {throw new IllegalArgumentException(NOT_EXIST_PROBLEM);}
        );
        problem.setNotificationDate(registerDto.getNotificationDate());
        if (!problem.getWriter().getId().equals(member.getId())) {
            throw new IllegalArgumentException(NOT_VALID_USER);
        }
        problemRepository.save(problem);
        reviewRepository.save(reviewMapper.toEntity(problem, registerDto));
    }
}
