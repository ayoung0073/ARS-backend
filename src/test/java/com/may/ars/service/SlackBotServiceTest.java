package com.may.ars.service;

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

}