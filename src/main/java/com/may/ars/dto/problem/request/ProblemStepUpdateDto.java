package com.may.ars.dto.problem.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ProblemStepUpdateDto {

    @NotNull
    private int step;

}
