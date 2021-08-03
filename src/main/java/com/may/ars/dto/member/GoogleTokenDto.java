package com.may.ars.dto.member;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class GoogleTokenDto {

    @NotBlank
    private String accessToken;

}
