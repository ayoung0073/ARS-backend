package com.may.ars.dto.problem;

import com.may.ars.model.entity.member.Member;
import com.may.ars.model.entity.problem.Problem;
import com.may.ars.model.entity.problem.Review;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemRegisterDto {

    private String title;

    private String link;

    private Member writer;

    private ArrayList<String> tagList;

    private String content;

    private int step;

    public void setWriter(Member writer) {
        this.writer = writer;
    }

    public Problem toProblemEntity() {
        return Problem.builder()
                .title(title)
                .link(link)
                .writer(writer)
                .build();
    }

    public Review toReviewEntity(Problem problem) {
        return Review.builder()
                .step(step)
                .problem(problem)
                .content(content)
                .build();
    }
}
