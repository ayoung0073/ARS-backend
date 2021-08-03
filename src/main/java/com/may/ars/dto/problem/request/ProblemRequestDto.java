package com.may.ars.dto.problem.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Builder
public class ProblemRequestDto {

    @NotBlank
    private String title;

    @NotNull
    private String link;

    @Builder.Default
    private int step = 1;

    @NotNull
    private LocalDate notificationDate;

    @NotNull
    private ArrayList<String> tagList;

    @NotBlank
    private String content;

}
