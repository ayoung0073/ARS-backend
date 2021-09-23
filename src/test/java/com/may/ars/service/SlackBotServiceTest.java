package com.may.ars.service;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.member.MemberRepository;
import com.may.ars.domain.problem.Problem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackBotServiceTest {

    @Autowired
    private SlackBotService slackBotService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private MemberRepository memberRepository;

    //    @Test
    void 유저_정보_By_이메일_테스트() {
        // given
        Member member = Member.builder()
                .email("ayong0310@naver.com")
                .slackId("test")
                .build();
        memberRepository.save(member);

        // when
        slackBotService.getSlackIdByEmail(member.getEmail());
    }

}
