package com.may.ars.dto.review;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ReviewRegisterDto {
    private String content;
    private int step;
    private LocalDate notificationDate;

    public Review toEntity(Problem problem) {
        return Review.builder()
                .content(content)
                .step(step)
                .problem(problem)
                .build();
    }
}
