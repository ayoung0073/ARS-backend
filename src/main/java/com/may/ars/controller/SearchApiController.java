package com.may.ars.controller;

import com.may.ars.dto.common.ResponseDto;
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

import static com.may.ars.common.message.SuccessMessage.SUCCESS_GET_SEARCH_LIST;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SearchApiController {

    private final SearchService searchService;
    private final ReviewMapper reviewMapper;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam(value = "search") String name) {
        List<SearchDto> searchList = searchService.search(name).stream()
                .map(reviewMapper::toSearchDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_GET_SEARCH_LIST, searchList));
    }
}
