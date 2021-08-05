package com.may.ars.utils.schedular;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackRegisterSchedulerTest {

    @Autowired
    private SlackRegisterScheduler scheduler;

    @Test
    void 슬랙_아이디_저장_테스트() {
        scheduler.registerSlackId();
    }

    @Test
    void 슬랙_알림_테스트()  {
        scheduler.notification();
    }

}