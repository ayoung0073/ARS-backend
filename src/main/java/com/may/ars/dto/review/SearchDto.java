package com.may.ars.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class SearchDto {

    private Long id;

    private String title;

    private int step;

    private String link;

    private LocalDate createdDate;

    private String content;

}
