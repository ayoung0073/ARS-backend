package com.may.ars.domain.review;

import com.may.ars.domain.problem.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

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

}