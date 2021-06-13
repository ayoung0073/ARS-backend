package com.may.ars.mapper;

import com.may.ars.domain.member.Member;
import com.may.ars.domain.problem.Problem;
import com.may.ars.dto.problem.ProblemRegisterDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class ProblemMapperTest {
    @Test
    void toEntity_테스트() {
        // given
        final ProblemRegisterDto registerDto = ProblemRegisterDto.builder()
                .content("hi")
                .title("hi")
                .step(3)
                .build();
        final Member member = null;
        // when
        final Problem problem = ProblemMapper.INSTANCE.toEntity(registerDto, member);
        //then
        assertNotNull(problem);
        assertThat(problem.getTitle(), is(registerDto.getTitle()));
    }
}