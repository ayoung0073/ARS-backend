package com.may.ars.dto.problem.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.domain.problem.ProblemTag;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ProblemOnlyDto {

    private Long id;

    private String title;

    private String link;

    private int step;

    private LocalDate notificationDate;

    @JsonIgnoreProperties({"problem"})
    private List<ProblemTag> tagList;
}
