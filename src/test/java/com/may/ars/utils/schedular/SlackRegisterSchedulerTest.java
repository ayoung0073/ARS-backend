package com.may.ars.utils.schedular;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SlackRegisterSchedulerTest {

    @Autowired
    private SlackRegisterScheduler scheduler;

    @Test
    void 슬랙_아이디_저장_테스트() throws JsonProcessingException {
        scheduler.registerSlackId();
    }

}