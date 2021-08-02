package com.may.ars.dto.guest;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GuestRequestDto {

    private String nickname;

    private String content;

}
