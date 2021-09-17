package com.may.ars.domain.problem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
class ProblemQueryRepositoryTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemQueryRepository problemQueryRepository;

    private final Tag tag1 = new Tag("테스트1");
    private final Tag tag2 = new Tag("테스트2");

    private Problem problem1;
    private Problem problem2;
    private Problem problem3;

    @BeforeEach
    void init() {

        String title1 = "테스트요1";
        String title2 = "테스트요2";
        String title3 = "테스트요3";

        problem1 = Problem.builder()
                .title(title1)
                .build();

        problem2 = Problem.builder()
                .title(title2)
                .build();

        problem3 = Problem.builder()
                .title(title3)
                .build();

        problem1.getTagList().add(new ProblemTag(problem1, tag1));
        problem2.getTagList().add(new ProblemTag(problem2, tag1));
        problem2.getTagList().add(new ProblemTag(problem2, tag2));
        problem3.getTagList().add(new ProblemTag(problem3, tag1));
        problem3.getTagList().add(new ProblemTag(problem3, tag2));

        // when
        problemRepository.save(problem1);
        problemRepository.save(problem2);
        problemRepository.save(problem3);

    }

    @Test
    void 최신_업데이트순으로_태그리스트_조회() {
        List<Problem> problemList = problemQueryRepository.findAllByTag(LocalDateTime.now(), 0L, tag1.getTagName(), 5);
        assertThat(problemList.size(), is(3));
        assertThat(problemList.get(0), is(problem3));
        assertThat(problemList.get(1), is(problem2));
        assertThat(problemList.get(2), is(problem1));
    }

    @Test
    void 입력된_수정된_날짜_이전_최신업데이트_태그리스트_조회() {
        List<Problem> problemList = problemQueryRepository.findAllByTag(problem3.getModifiedDate(), 0L, tag2.getTagName(), 5);
        assertThat(problemList.size(), is(1));
        assertThat(problemList.get(0), is(problem2));
    }

    @Test
    void 입력된_수정된_날짜_이전_id_중복제거_최신업데이트_태그리스트_조회() {
        List<Problem> problemList = problemQueryRepository.findAllByTag(problem3.getModifiedDate(), problem3.getId(), tag2.getTagName(), 5);
        assertThat(problemList.size(), is(1));
        assertThat(problemList.get(0), is(problem2));
    }

}