package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.ars.common.advice.exception.JsonWriteException;
import com.may.ars.domain.problem.Problem;
import com.may.ars.dto.slack.Attachment;
import com.may.ars.dto.slack.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackBotService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${slack.token}")
    private String slackToken;

    public void notification(Problem problem) {
        String url = "https://slack.com/api/chat.postMessage";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json; charset=utf-8");

        Message message = Message.builder()
                .channel(problem.getWriter().getSlackId())
                .text(problem.getTitle() + " 문제를 풀 시간입니다!")
                .attachments(List.of(Attachment.builder()
                        .text("<" + problem.getLink() + "|" + problem.getTitle() + "> 문제를 푸는 시간입니다! \uD83D\uDE00 \n 리뷰를 추가하려면 <https://ars.vercel.app/|\uD83D\uDC49 여기 \uD83D\uDC48>를 눌러주세요.")
                        .build())
                )
                .build();

        HttpEntity<Message> requestEntity = new HttpEntity<>(message, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        String response = responseEntity.getBody();
        log.info(response);
    }

    public String getSlackIdByEmail(String email) {
        String url = "https://slack.com/api/users.lookupByEmail";
        url += "?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> restResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        int status = restResponse.getStatusCode().value();
        String response = restResponse.getBody();
        log.info("Response status: " + status);
        log.info(response);
        JsonNode body;
        try{
            body = objectMapper.readTree(restResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new JsonWriteException();
        }

        if (body.get("ok").asBoolean())
            return body.get("user").get("id").textValue();
        else
            return null;
    }

}
