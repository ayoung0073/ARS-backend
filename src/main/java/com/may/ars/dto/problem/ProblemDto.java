package com.may.ars.dto.problem;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProblemDto extends Problem {
    private Long id;
    private String title;
    private Member writer;
    private String link;
    private List<Review> reviewList;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
