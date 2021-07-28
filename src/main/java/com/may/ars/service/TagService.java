package com.may.ars.service;

import com.may.ars.domain.problem.TagQueryRepository;
import com.may.ars.dto.problem.response.TagDto;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagQueryRepository tagQueryRepository;

    public List<TagDto> getAllTagList() {
        List<TagDto> tagList = new ArrayList<>();
        List<Tuple> list = tagQueryRepository.findAllTagList();
        list.forEach(tuple -> tagList.add(TagDto.builder().tagName(tuple.get(0, String.class)).count(tuple.get(1, Long.class)).build()));
        return tagList;
    }
}
