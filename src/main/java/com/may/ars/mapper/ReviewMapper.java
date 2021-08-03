package com.may.ars.mapper;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.dto.review.SearchDto;
import com.may.ars.dto.review.ReviewRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "id", ignore = true)
    Review toEntity(Problem problem, ProblemRequestDto requestDto);

    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "id", ignore = true)
    Review toEntity(Problem problem, ReviewRequestDto requestDto);

    @Mapping(target = "problem", ignore = true)
    @Mapping(target = "id", source = "id")
    Review toEntity(Long id, ReviewRequestDto requestDto);

    @Mapping(target = "id", source="problem.id")
    @Mapping(target = "title", source="problem.title")
    @Mapping(target = "step", source="problem.step")
    @Mapping(target = "link", source="problem.link")
    SearchDto toSearchDto(Review review);
}
