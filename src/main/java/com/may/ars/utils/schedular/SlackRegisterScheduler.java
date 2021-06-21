package com.may.ars.utils.schedular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.may.ars.domain.member.Member;
import com.may.ars.service.MemberService;
import com.may.ars.service.SlackBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackRegisterScheduler {

    private final SlackBotService slackBotService;
    private final MemberService memberService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정마다
    public void registerSlackId() throws JsonProcessingException {
        List<Member> memberList = memberService.findAllBySlackIdNull();
        for (Member member : memberList) {
            String slackId = slackBotService.getSlackIdByEmail(member.getEmail());
            if (slackId != null) {
                member.setSlackId(slackId);
                memberService.save(member);
            }
        }
    }
}
