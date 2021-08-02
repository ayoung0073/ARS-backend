package com.may.ars.dto.problem.request;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProblemRequestDto {

    private String title;

    private String link;

    private int step;

    private LocalDate notificationDate;

    private ArrayList<String> tagList;

    private String content;

}
