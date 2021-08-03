package com.may.ars.dto.problem.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProblemRequestDto {

    @NotBlank
    private String title;

    @NotNull
    private String link;

    @NotNull
    private int step;

    @NotNull
    private LocalDate notificationDate;

    @NotNull
    private ArrayList<String> tagList;

    @NotBlank
    private String content;

}
