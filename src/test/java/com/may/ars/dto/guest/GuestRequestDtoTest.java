package com.may.ars.dto.guest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GuestRequestDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @Test
    void 빈칸_유효성X_테스트() {

        // given
        GuestRequestDto requestDto = GuestRequestDto.builder()
                .nickname("   ")
                .content("테스트")
                .build();

        // when
        Set<ConstraintViolation<GuestRequestDto>> violations = validator.validate(requestDto); // 유효하지 않은 경우 violations 값을 가지고 있다.

        // then
        assertThat(violations).isNotEmpty();

    }

    @Test
    void 빈칸_유효성O_테스트() {

        // given
        GuestRequestDto requestDto = GuestRequestDto.builder()
                .nickname("테스트")
                .content("테스트")
                .build();

        // when
        Set<ConstraintViolation<GuestRequestDto>> violations = validator.validate(requestDto); // 유효하지 않은 경우 violations 값을 가지고 있다.

        // then
        assertThat(violations).isEmpty();

    }
}