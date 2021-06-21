package com.may.ars.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class SlackBotService {

    @Value("${slack.token}")
    private String slackToken;

    public void test() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = "https://slack.com/api/chat.postEphemeral";
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json");
        String body = "{\"channel\":\"todo\", \"text\" : \"test\", \"user\" : \"U01KYPCH6G5\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        log.info("Response status: " + status);
        log.info(response);
    }

}
