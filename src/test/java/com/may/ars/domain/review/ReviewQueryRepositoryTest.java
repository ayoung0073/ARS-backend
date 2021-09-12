package com.may.ars.domain.review;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.dto.problem.response.ProblemOnlyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ReviewQueryRepositoryTest {

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Test
    @Transactional
    void 문제중복없이_리뷰_최신등록순_조회_테스트() {
        // given
        Problem problem = saveReviewList();

        // when
        List<ProblemOnlyDto> problemList = reviewQueryRepository.getReviewList(problem.getReviewList().get(2).getId() + 1, 3);

        problem.getReviewList().forEach(p -> System.out.println(p.getId() + " " + p.getProblem().getTitle()));
        problemList.forEach(p -> System.out.println(p.getReviewId() + " " + p.getTitle()));
        // then
        assertThat(problemList.get(0).getProblemId(), is(problem.getId()));
    }

    Problem saveReviewList() {
        Problem problem = Problem.builder().build();
        Review review1 = Review.builder()
                .problem(problem)
                .content("review1")
                .build();
        Review review2 = Review.builder()
                .problem(problem)
                .content("review2")
                .build();
        Review review3 = Review.builder()
                .problem(problem)
                .content("review3")
                .build();

        problem.getReviewList().add(review1);
        problem.getReviewList().add(review2);
        problem.getReviewList().add(review3);
        return problemRepository.save(problem);
    }
}