package com.may.ars;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableJpaAuditing
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")
@SpringBootApplication
public class ArsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArsApplication.class, args);
    }

}
