package com.may.ars.service;

import com.may.ars.domain.problem.*;
import com.may.ars.domain.review.Review;
import com.may.ars.domain.review.ReviewRepository;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Collections.singletonList;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @Transactional
    @DisplayName("문제, 리뷰 등록 테스트")
    void 문제_리뷰_등록_테스트() {
        Problem problem = Problem.builder().build();
        Review review = Review.builder()
                .problem(problem)
                .content("테스트")
                .build();

        problem.setReviewList(singletonList(review));
        problemRepository.save(problem);

        Problem saveProblem = problemService.getProblemById(problem.getId());
        assertThat(reviewRepository.existsById(review.getId()), is(true));
        assertThat(saveProblem.getReviewList().size(), is(1));
    }

    @Test
    @Transactional
    @DisplayName("문제, 태그 등록 테스트")
    void 문제_태그_등록_테스트() {
        Problem problem = Problem.builder().build();
        List<String> tagStringList = List.of("테스트1", "테스트2");
        List<ProblemTag> tagList = tagStringList.stream().map(tag -> new ProblemTag(problem, new Tag(tag))).collect(toList());
        problem.setTagList(tagList);
        problemRepository.save(problem);

        Problem saveProblem = problemService.getProblemById(problem.getId());
        assertThat(saveProblem.getTagList().size(), is(tagStringList.size()));
    }

    @Test
    @Transactional
    @DisplayName("문제, 태그 중복 저장 테스트")
    void 태그_중복_저장_테스트() {
        Tag saveTag = new Tag("테스트1");
        tagRepository.save(saveTag);
        Problem problem = Problem.builder().build();
        List<String> tagStringList = List.of("테스트1", "테스트2");
        List<ProblemTag> tagList = tagStringList.stream()
                .map(tagName -> tagRepository.findByTagName(tagName)
                        .map(
                                tag -> new ProblemTag(problem, tag)).orElseGet(
                                () -> new ProblemTag(problem, new Tag(tagName))
                        )).collect(toList());
        problem.setTagList(tagList);
        problemRepository.save(problem);

        Problem saveProblem = problemService.getProblemById(problem.getId());
        assertThat(saveProblem.getTagList().size(), is(tagStringList.size()));
    }

    @Test
    @Transactional
    @DisplayName("문제 저장 통합 테스트")
    void 문제_저장_테스트() {
        Tag saveTag = new Tag("테스트1");
        tagRepository.save(saveTag);
        Problem problem = Problem.builder().build();
        ArrayList<String> tagStringList = new ArrayList<>(Arrays.asList("테스트1", "테스트2"));
        ProblemRequestDto requestDto = ProblemRequestDto.builder()
                .tagList(tagStringList)
                .content("테스트")
                .build();
        problemService.registerProblem(problem, requestDto);

        Problem saveProblem = problemService.getProblemById(problem.getId());
        assertThat(saveProblem.getTagList().size(), is(tagStringList.size()));
        assertThat(saveProblem.getReviewList().get(0).getContent(), is(requestDto.getContent()));
    }
}
