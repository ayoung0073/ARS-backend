package com.may.ars.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

@Builder
@Getter
public class ReviewRequestDto {
    // Problem 관련 업데이트도 같이 Request 함
    // TODO Problem, Review 함께 있는 거 분리?
    private String title;

    private String link;

    private ArrayList<String> tagList;

    private String content;

    private LocalDate notificationDate;
}
