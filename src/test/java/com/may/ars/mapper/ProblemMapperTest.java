package com.may.ars.mapper;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.dto.problem.response.ProblemDto;
import com.may.ars.dto.problem.response.ProblemOnlyDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class ProblemMapperTest {
    @Test
    void toEntity_테스트() {
        // given
        final ProblemRequestDto registerDto = ProblemRequestDto.builder()
                .content("hi")
                .title("hi")
                .step(3)
                .build();
        final Member member = null;
        // when
        final Problem problem = ProblemMapper.INSTANCE.toEntity(registerDto, member);
        //then
        assertNotNull(problem);
        assertThat(problem.getTitle(), is(registerDto.getTitle()));
    }

    @Test
    void 리뷰목록_포함_toDto_테스트() {
        // given
        final Problem problem = Problem.builder()
                .writer(null)
                .link("test.com")
                .title("test")
                .tagList(null)
                .notificationDate(LocalDate.of(2021, 7, 3))
                .reviewList(null)
                .build();
        // when
        final ProblemDto problemDto = ProblemMapper.INSTANCE.toDto(problem);
        // given
        assertThat(problemDto.getTitle(), is(problem.getTitle()));
        assertThat(problemDto.getReviewList(), is(problem.getReviewList()));
    }

    @Test
    void 리뷰목록_포함X_toDto_테스트() {
        // given
        final Problem problem = Problem.builder()
                .writer(null)
                .link("test.com")
                .title("test")
                .tagList(null)
                .notificationDate(LocalDate.of(2021, 7, 3))
                .reviewList(null)
                .build();
        // when
        final ProblemOnlyDto problemDto = ProblemMapper.INSTANCE.toReviewExcludeDto(problem);
        // given
        assertThat(problemDto.getTitle(), is(problem.getTitle()));
    }
}