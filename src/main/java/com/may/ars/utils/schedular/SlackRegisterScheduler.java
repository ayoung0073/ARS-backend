package com.may.ars.utils.schedular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.member.MemberRepository;
import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.service.SlackBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackRegisterScheduler {

    private final SlackBotService slackBotService;
    private final MemberRepository memberRepository;
    private final ProblemRepository problemRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정마다
    public void registerSlackId() throws JsonProcessingException {
        List<Member> memberList = memberRepository.findAllBySlackIdNull();
        for (Member member : memberList) {
            String slackId = slackBotService.getSlackIdByEmail(member.getEmail());
            if (slackId != null) {
                member.setSlackId(slackId);
                memberRepository.save(member);
            }
        }
    }

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시마다
    public void notification() {
        List<Problem> problemList = problemRepository.findAllByNotificationDate(LocalDate.now());
        for (Problem problem : problemList) {
            slackBotService.notification(problem);
        }
    }
}
