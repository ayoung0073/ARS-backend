package com.may.ars.domain.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_수정_테스트() {
        // given
        Long reviewId = 1L;
        String content = "TEST";
        Review review = Review.builder()
                .id(reviewId)
                .content(content)
                .step(1)
                .build();

        // when
        reviewRepository.save(review);

        // then
        Review result = reviewRepository.findById(reviewId).get();
        assertThat(result.getContent(), is(content));
    }
}