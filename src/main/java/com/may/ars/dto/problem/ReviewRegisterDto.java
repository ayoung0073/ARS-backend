package com.may.ars.dto.problem;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.Review;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewRegisterDto {
    private String content;
    private int step;

    public Review toEntity(Problem problem) {
        return Review.builder()
                .content(content)
                .step(step)
                .problem(problem)
                .build();
    }
}
