package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TagApiController {

    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<?> getProblemListByTag() {
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "태그 가져오기", tagService.getAllTagList());
        return ResponseEntity.ok().body(response);
    }
}
