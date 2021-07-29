package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.review.SearchDto;
import com.may.ars.mapper.ReviewMapper;
import com.may.ars.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SearchApiController {

    private final SearchService searchService;
    private final ReviewMapper reviewMapper;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam String search) {
        List<SearchDto> searchList = searchService.search(search).stream()
                                                                 .map(reviewMapper::toSearchDto)
                                                                 .collect(Collectors.toList());
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "검색 결과입니다.", searchList);
        return ResponseEntity.ok().body(response);
    }
}
