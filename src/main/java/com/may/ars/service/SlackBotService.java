package com.may.ars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackBotService {

    @Value("${slack.token}")
    private String slackToken;

    public void test() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = "https://slack.com/api/chat.postMessage";
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json");
        String body = "{\"channel\":\"todo\", \"text\" : \"test\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
    }

}
