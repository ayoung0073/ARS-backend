package com.may.ars.domain.problem;

import com.may.ars.domain.review.Review;
import com.may.ars.domain.review.ReviewQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
class ProblemQueryRepositoryTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    @Autowired
    private ProblemTagRepository problemTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void 태그로_문제리스트_조회_성공_테스트() {
        // given
        String tagName = "ㅌㅅㅌ";
        String title = "테스트요";

        Problem problem = Problem.builder()
                .title(title)
                .build();

        // when
        Tag tag = new Tag(tagName);
        ProblemTag problemTag = new ProblemTag(problem, tag);
        problem.setTagList(Collections.singletonList(problemTag));

        problemRepository.save(problem);
        problemTagRepository.save(problemTag);
        tagRepository.save(tag);

        // then
        List<Review> reviewList = reviewQueryRepository.findAllByTag(tagName, 1L, PageRequest.of(0, 5));
        assertThat(reviewList.size(), is(1));
//        assertThat(reviewList.get(0).getTitle(), is(title));
    }

}