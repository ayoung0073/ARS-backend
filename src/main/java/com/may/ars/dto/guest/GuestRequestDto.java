package com.may.ars.dto.guest;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class GuestRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String content;

}
