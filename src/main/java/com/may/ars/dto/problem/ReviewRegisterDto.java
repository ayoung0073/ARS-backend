package com.may.ars.dto.problem;

import com.may.ars.model.entity.member.Member;
import com.may.ars.model.entity.problem.Problem;
import com.may.ars.model.entity.problem.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
