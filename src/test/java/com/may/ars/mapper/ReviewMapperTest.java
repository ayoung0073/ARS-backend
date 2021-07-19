package com.may.ars.mapper;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import com.may.ars.dto.problem.ProblemRequestDto;
import com.may.ars.dto.problem.ProblemRegisterDto;
import com.may.ars.dto.review.ReviewRequestDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTest {
    @Test
    void 등록_toEntity_테스트() {
        // given
        final ProblemRequestDto registerDto = ProblemRequestDto.builder()
                .content("hi")
                .title("hi")
                .step(3)
                .build();
        final Member member = null;
        final Problem problem = ProblemMapper.INSTANCE.toEntity(registerDto, member);

        // when
        final Review review = ReviewMapper.INSTANCE.toEntity(problem, registerDto);

        //then
        assertNotNull(review);
        assertThat(review.getContent(), is(registerDto.getContent()));
    }

    @Test
    void 수정_toEntity_테스트() {
        // given
        final ReviewRequestDto requestDto = ReviewRequestDto.builder()
                .content("hi")
                .build();

        // when
        final Review review = ReviewMapper.INSTANCE.toEntity(1L, requestDto);

        //then
        assertNotNull(review);
        assertThat(review.getContent(), is(requestDto.getContent()));
    }
}