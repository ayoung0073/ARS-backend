package com.may.ars.domain.problem;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProblemRepositoryTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 문제_존재X_테스트() {
        // given
        Member member = memberRepository.findByEmail("ayong703@gmail.com").get();

        // when
        Optional<Problem> problem = problemRepository.findProblemByIdAndWriter(1L, member);

        // then
        assertThat(problem.isEmpty(), is(problem.isEmpty()));
    }

    @Test
    void 문제_존재O_테스트() {
        // given
        Member member = memberRepository.findByEmail("ayong703@naver.com").get();

        // when
        Optional<Problem> problem = problemRepository.findProblemByIdAndWriter(1L, member);

        // then
        assertThat(problem.isPresent(), is(true));
    }

    @Test
    void 문제_삭제_테스트() {
        problemRepository.deleteProblemById(1L);
    }


    @Test
    void 문제_수정_테스트() {
        // given
        Member member = memberRepository.findByEmail("ayong703@gmail.com").get();
        String title = "테스트";
        String link = "test.com";
        Problem problem = Problem.builder()
                .id(1L)
                .title(title)
                .writer(member)
                .link(link)
                .build();

        // when
        problemRepository.save(problem);

        // then
        Problem updatedProblem = problemRepository.findById(1L).get();

        assertThat(updatedProblem.getTitle(), is(title));
        assertThat(updatedProblem.getLink(), is(link));
    }

}