package com.may.ars.mapper;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.dto.problem.ProblemRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

    ProblemMapper INSTANCE = Mappers.getMapper(ProblemMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "writer", source = "member")
    @Mapping(target = "tagList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    Problem toEntity(ProblemRequestDto requestDto, Member member);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "writer", source = "member")
    @Mapping(target = "tagList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    Problem toEntity(Long id, ProblemRequestDto requestDto, Member member);
}
