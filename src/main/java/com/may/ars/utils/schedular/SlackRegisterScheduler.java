package com.may.ars.utils.schedular;

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
    private final ProblemRepository problemRepository;

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시마다
    public void notification() {
        List<Problem> problemList = problemRepository.findAllByNotificationDate(LocalDate.now());
        for (Problem problem : problemList) {
            slackBotService.notification(problem);
        }
    }
}
