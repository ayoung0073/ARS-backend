package com.may.ars.mapper;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.dto.review.SearchDto;
import com.may.ars.dto.review.ReviewRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewMapperTest {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    void 등록_toEntity_테스트() {
        // given
        final ProblemRequestDto registerDto = ProblemRequestDto.builder()
                .content("hi")
                .title("hi")
                .step(3)
                .build();
        final Member member = null;
        final Problem problem = problemMapper.toEntity(registerDto, member);

        // when
        final Review review = reviewMapper.toEntity(problem, registerDto);

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
        final Review review = reviewMapper.toEntity(1L, requestDto);

        //then
        assertNotNull(review);
        assertThat(review.getContent(), is(requestDto.getContent()));
    }

    @Test
    void toSearchDto_테스트() {
        // given
        Problem problem = Problem.builder()
                .title("테스트")
                .link("test.com")
                .step(3)
                .build();
        Review review = Review.builder()
                .content("내용 테스트")
                .problem(problem)
                .build();

        // when
        SearchDto searchDto = reviewMapper.toSearchDto(review);

        // then
        assertThat(searchDto.getTitle(), is(problem.getTitle()));
        assertThat(searchDto.getStep(), is(problem.getStep()));
        assertThat(searchDto.getLink(), is(problem.getLink()));
        assertThat(searchDto.getContent(), is(review.getContent()));
        assertThat(searchDto.getCreatedDate(), is(review.getCreatedDate()));
        assertThat(searchDto.getId(), is(problem.getId()));
    }
}