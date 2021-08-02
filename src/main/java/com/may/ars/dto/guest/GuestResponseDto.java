package com.may.ars.dto.guest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class GuestResponseDto {

    private String nickname;

    private String content;

    private LocalDate createdDate;

}
