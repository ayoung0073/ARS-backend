package com.may.ars.mapper;

import com.may.ars.domain.problem.Problem;
import com.may.ars.domain.review.Review;
import com.may.ars.dto.problem.ProblemRegisterDto;
import com.may.ars.dto.problem.ReviewRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "id", ignore = true)
    Review toEntity(Problem problem, ProblemRegisterDto registerDto);

    @Mapping(target = "problem", source = "problem")
    @Mapping(target = "id", ignore = true)
    Review toEntity(Problem problem, ReviewRegisterDto registerDto);
}
