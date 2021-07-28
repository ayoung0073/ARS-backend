package com.may.ars.dto.problem.request;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemRegisterDto {

    private String title;

    private String link;

    private Member writer;

    private ArrayList<String> tagList;

    private String content;

    private int step;

    private LocalDate notificationDate;

}