package com.may.ars.dto.problem;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProblemRequestDto {

    private String title;

    private String link;

    private int step;

    private LocalDate notificationDate;

    private ArrayList<String> tagList;

    private String content;

}
