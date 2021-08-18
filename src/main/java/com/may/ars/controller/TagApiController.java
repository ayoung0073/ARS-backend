package com.may.ars.controller;

import com.may.ars.dto.common.ResponseDto;
import com.may.ars.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.may.ars.common.message.SuccessMessage.SUCCESS_GET_TAG_LIST;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TagApiController {

    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<?> getProblemListByTag() {
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_GET_TAG_LIST, tagService.getAllTagList())
        );
    }
}
