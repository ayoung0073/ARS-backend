package com.may.ars.domain.review;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void 리뷰_수정_테스트() {
        // given
        String content = "TEST";
        Review review = Review.builder()
                .content(content)
                .build();

        // when
        reviewRepository.save(review);

        // then
        Review result = reviewRepository.findById(review.getId()).get();
        assertThat(result.getContent(), is(content));
    }

    @Test
    void 문제중복없이_리뷰_최신등록순_조회_테스트() {
        // given
        Problem problem = saveReviewList();

        // when
        List<Review> reviewList = reviewRepository.findFirstByIdLessThanOrderByIdDesc(problem.getReviewList().get(2).getId() + 1, PageRequest.of(0, 3));

        // then
        assertThat(reviewList.size(), is(1));
        assertThat(reviewList.get(0).getContent(), is(problem.getReviewList().get(2).getContent()));
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