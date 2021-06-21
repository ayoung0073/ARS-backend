package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackBotService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${slack.token}")
    private String slackToken;

    public void test() {
        HttpHeaders headers = new HttpHeaders();
        String url = "https://slack.com/api/chat.postEphemeral";
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json");
        String body = "{\"channel\":\"todo\", \"text\" : \"test\", \"user\" : \"U01KYPCH6G5\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        log.info("Response status: " + status);
        log.info(response);
    }

    public String getSlackIdByEmail(String email) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        String url = "https://slack.com/api/users.lookupByEmail";
        url += "?email=" + email;
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> restResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        HttpStatus httpStatus = restResponse.getStatusCode();
        int status = httpStatus.value();
        String response = restResponse.getBody();
        log.info("Response status: " + status);
        log.info(response);
        JsonNode body = objectMapper.readTree(restResponse.getBody());
        if (body.get("ok").asBoolean())
            return body.get("user").get("id").textValue();
        else
            return null;
    }

}
