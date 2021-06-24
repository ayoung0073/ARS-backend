package com.may.ars.dto.problem;


import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemUpdateDto {
    private String title;

    private String link;

    private ArrayList<String> tagList;

    private LocalDate notificationDate;
}
