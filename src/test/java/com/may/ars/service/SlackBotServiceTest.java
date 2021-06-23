package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.may.ars.domain.problem.Problem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlackBotServiceTest {

    @Autowired
    private SlackBotService slackBotService;

    @Autowired
    private ProblemService problemService;

    @Test
    void 메세지_전송_테스트() {
        Problem problem = problemService.getProblemById(1L);
        slackBotService.notification(problem);
    }

    @Test
    void 유저_정보_By_이메일_테스트() throws JsonProcessingException {
        slackBotService.getSlackIdByEmail("ayong703@gmail.com");
    }

}