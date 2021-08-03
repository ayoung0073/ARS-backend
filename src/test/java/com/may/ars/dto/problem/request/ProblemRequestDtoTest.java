package com.may.ars.dto.problem.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProblemRequestDtoTest {

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
    void 리스트_NotNull_유효O_테스트() {

        // given
        ProblemRequestDto requestDto = ProblemRequestDto.builder()
                .title("테스트")
                .content("테스트")
                .link("ㄹㄴㅇ")
                .notificationDate(LocalDate.of(2021, 7, 3))
                .step(3)
                .tagList(new ArrayList<>(Arrays.asList("테스트1", "테스트2", "테스트3")))
                .build();

        // when
        Set<ConstraintViolation<ProblemRequestDto>> violations = validator.validate(requestDto);

        // then
        assertThat(violations).isEmpty();

    }

    @Test
    void 리스트_NotNull_유효X_테스트() {

        // given
        ProblemRequestDto requestDto = ProblemRequestDto.builder()
                .title("테스트")
                .content("테스트")
                .link("test.com")
                .notificationDate(LocalDate.of(2021, 7, 3))
                .step(3)
                .tagList(null)
                .build();

        // when
        Set<ConstraintViolation<ProblemRequestDto>> violations = validator.validate(requestDto);

        // then
        assertThat(violations).isNotEmpty();

    }

    @Test
    void int_NotNull_유효성_테스트() {

        // given
        ProblemRequestDto requestDto = ProblemRequestDto.builder()
                .title("테스트")
                .content("테스트")
                .link("test.com")
                .notificationDate(LocalDate.of(2021, 7, 3))
                .tagList(new ArrayList<>(Arrays.asList("테스트1", "테스트2", "테스트3")))
                .build();

        // when
        Set<ConstraintViolation<ProblemRequestDto>> violations = validator.validate(requestDto);

        // then
        assertThat(violations).isEmpty();
        assertThat(requestDto.getStep()).isEqualTo(1);

    }

}