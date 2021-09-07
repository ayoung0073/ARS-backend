package com.may.ars.utils.scheduler;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.problem.ProblemRepository;
import com.may.ars.service.SlackBotService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SlackScheduler {

    private final SlackBotService slackBotService;
    private final ProblemRepository problemRepository;

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시마다
    @SchedulerLock(name = "SlackScheduler_notification")
    public void notification() {
        List<Problem> problemList = problemRepository.findAllByNotificationDate(LocalDate.now());
        problemList.forEach(slackBotService::notification);
    }
}
