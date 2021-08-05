package com.may.ars.domain.problem;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class ProblemRepositoryTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;


     @Test
    void 문제_존재X_테스트() {
         // given
         final Member member = Member.builder()
                 .email("ayong703@gmail.com")
                 .build();

         final Problem problem = Problem.builder()
                 .writer(member)
                 .build();

         memberRepository.save(member);
         problemRepository.save(problem);

        // when
        Optional<Problem> optional = problemRepository.findProblemByIdAndWriter(problem.getId() + 1, member);

        // then
        assertThat(optional.isEmpty(), is(true));
    }

    @Test
    void 문제_존재O_테스트() {
        // given
        final Member member = Member.builder()
                .email("ayong703@gmail.com")
                .build();

        final Problem problem = Problem.builder()
                .writer(member)
                .build();

        memberRepository.save(member);
        problemRepository.save(problem);

        // when
        Optional<Problem> optional = problemRepository.findProblemByIdAndWriter(2L, member);

        // then
        assertThat(optional.isPresent(), is(true));
    }

    @Test
    void 문제_삭제_테스트() {
        // given
        final Member member = Member.builder()
                .email("ayong703@gmail.com")
                .build();

        final Problem problem = Problem.builder()
                .writer(member)
                .build();

        memberRepository.save(member);
        problemRepository.save(problem);

        problemRepository.deleteProblemById(problem.getId());
    }


    @Test
    void 문제_수정_테스트() {
        // given
        String title = "테스트";

        String link = "test.com";

        final Member member = Member.builder()
                .email("ayong703@gmail.com")
                .build();
        final Problem problem = Problem.builder()
                .writer(member)
                .build();


        memberRepository.save(member);
        problemRepository.save(problem);

        // when
        problem.setTitle(title);
        problem.setLink(link);

        problemRepository.save(problem);

        // then
        Problem updatedProblem = problemRepository.findById(problem.getId()).get();

        assertThat(updatedProblem.getTitle(), is(title));
        assertThat(updatedProblem.getLink(), is(link));
    }
}