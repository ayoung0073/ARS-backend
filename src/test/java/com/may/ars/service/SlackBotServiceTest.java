package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlackBotServiceTest {

    @Autowired
    private SlackBotService slackBotService;

    @Test
    void 메세지_전송_테스트() {
        slackBotService.test();
    }

    @Test
    void 유저_정보_By_이메일_테스트() throws JsonProcessingException {
        slackBotService.getSlackIdByEmail("ayong703@gmail.com");
    }

}