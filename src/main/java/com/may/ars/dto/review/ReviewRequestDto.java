package com.may.ars.dto.review;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

@Builder
@Getter
public class ReviewRequestDto {

    @NotBlank
    private String title;

    @NotNull
    private String link;

    @NotNull
    private ArrayList<String> tagList;

    @NotBlank
    private String content;

    @NotNull
    private LocalDate notificationDate;

}
