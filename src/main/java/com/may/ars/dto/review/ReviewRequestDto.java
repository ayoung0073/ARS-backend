package com.may.ars.dto.review;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ReviewRequestDto {
    private String content;
    private int step;
    private LocalDate notificationDate;
}
