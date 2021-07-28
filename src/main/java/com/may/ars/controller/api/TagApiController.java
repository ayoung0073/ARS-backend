package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.problem.response.ProblemOnlyDto;
import com.may.ars.mapper.ProblemMapper;
import com.may.ars.service.ProblemService;
import com.may.ars.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags")
public class TagApiController {

    private final ProblemMapper problemMapper;
    private final ProblemService problemService;
    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<?> getProblemListByTag(@RequestParam String name) {
        List<ProblemOnlyDto> problemList = problemService.getProblemListByTagName(name).stream()
                .map(problemMapper::toReviewExcludeDto)
                .collect(Collectors.toList());
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "문제 가져오기", problemList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getProblemListByTag() {
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "태그 가져오기", tagService.getAllTagList());
        return ResponseEntity.ok().body(response);
    }
}
