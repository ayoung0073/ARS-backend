package com.may.ars.dto.problem.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.domain.problem.ProblemTag;
import com.may.ars.domain.review.Review;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ProblemDto {

    private Long id;

    private String title;

    private String link;

    private int step;

    private LocalDate notificationDate;

    @JsonIgnoreProperties({"problem"})
    private List<Review> reviewList;

    @JsonIgnoreProperties({"problem"})
    private List<ProblemTag> tagList;
}
