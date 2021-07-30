package com.may.ars.mapper;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.dto.problem.response.ProblemDto;
import com.may.ars.dto.problem.response.ProblemOnlyDto;
import com.may.ars.dto.review.ReviewRequestDto;
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

    // 리뷰와 함께 업데이트할 때 Problem 으로 매핑
    @Mapping(target = "id", source = "id")
    @Mapping(target = "writer", source = "member")
    @Mapping(target = "tagList", ignore = true)
    @Mapping(target = "step", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    Problem toEntity(Long id, ReviewRequestDto requestDto, Member member);

    ProblemDto toDto(Problem problem);

    ProblemOnlyDto toReviewExcludeDto(Problem problem);
}
