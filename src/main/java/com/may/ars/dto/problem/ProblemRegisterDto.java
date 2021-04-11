package com.may.ars.dto.problem;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemRegisterDto {

    private String title;

    private String link;

    private ArrayList<String> tagList;

    private String review;

}
