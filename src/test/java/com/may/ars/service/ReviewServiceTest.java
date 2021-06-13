package com.may.ars.service;

import com.may.ars.dto.member.MemberDto;
import com.may.ars.dto.problem.ReviewRegisterDto;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.domain.problem.ReviewRepository;
import com.may.ars.response.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProblemRepository problemRepository;

    void setup() {
        final Member member = Member.builder()
                .email("test")
                .id(1L)
                .nickname("test")
                .build();

        final Problem problem = Problem.builder()
                .id(1L)
                .writer(member)
                .build();

        given(problemRepository.findById(1L)).willReturn(Optional.of(problem));
    }

    @Test
    @DisplayName("리뷰 등록 테스트")
    void 리뷰_등록_성공_테스트() {
        setup();
        // given
        final Member member = Member.builder()
                .id(1L)
                .build();

        Long problemId = 1L;

        final ReviewRegisterDto registerDto = ReviewRegisterDto.builder()
                .content("리뷰 등록 테스트")
                .step(3)
                .build();

        // when
        reviewService.registerReview(problemId, registerDto,  member);
    }

    @Test
    @DisplayName("리뷰 등록 실패 테스트 - 문제 존재하지 않는 경우")
    void 리뷰_등록_문제X_테스트() {
        //given
        given(problemRepository.findById(2L)).willReturn(Optional.empty());
        final Member member = Member.builder()
                .id(2L)
                .build();

        Long problemId = 2L;

        final ReviewRegisterDto registerDto = ReviewRegisterDto.builder()
                .content("리뷰 등록 테스트")
                .step(3)
                .build();

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> reviewService.registerReview(problemId, registerDto, member)); // 예외가 발생해야 한다.

        //then
        assertThat(e.getMessage(), is(ErrorMessage.NOT_EXIST_PROBLEM));
    }

    @Test
    @DisplayName("리뷰 등록 실패 테스트 - 권한 없는 경우")
    void 리뷰_등록_권한X_테스트() {
        setup();
        //given
        final Member member = Member.builder()
                .id(2L)
                .build();

        Long problemId = 1L;

        final ReviewRegisterDto registerDto = ReviewRegisterDto.builder()
                .content("리뷰 등록 테스트")
                .step(3)
                .build();

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> reviewService.registerReview(problemId, registerDto, member)); // 예외가 발생해야 한다.

        //then
        assertThat(e.getMessage(), is(ErrorMessage.NOT_VALID_USER));
    }
}
