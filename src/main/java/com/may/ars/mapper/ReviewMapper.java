package com.may.ars.mapper;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.dto.problem.response.ProblemDto;
import com.may.ars.dto.problem.response.ProblemOnlyDto;
import com.may.ars.dto.review.SearchDto;
import com.may.ars.dto.review.ReviewRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "id", ignore = true)
    Review toEntity(Problem problem, ProblemRequestDto requestDto);

    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "id", ignore = true)
    Review toEntity(Problem problem, ReviewRequestDto requestDto);

    @Mapping(target = "problem", ignore = true)
    @Mapping(target = "id", source = "id")
    Review toEntity(Long id, ReviewRequestDto requestDto);

    @Mapping(target = "id", source="id")
    @Mapping(target = "problemId", source="problem.id")
    @Mapping(target = "title", source="problem.title")
    @Mapping(target = "step", source="problem.step")
    @Mapping(target = "link", source="problem.link")
    SearchDto toSearchDto(Review review);

    @Mapping(target = "problemId", source="problem.id")
    @Mapping(target = "title", source="problem.title")
    @Mapping(target = "step", source="problem.step")
    @Mapping(target = "notificationDate", source="problem.notificationDate")
    @Mapping(target = "link", source="problem.link")
    @Mapping(target = "tagList", source="problem.tagList")
    @Mapping(target = "reviewId", source="id")
    ProblemOnlyDto toProblemDto(Review review);
}
